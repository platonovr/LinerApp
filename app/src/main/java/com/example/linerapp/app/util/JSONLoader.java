package com.example.linerapp.app.util;

import android.util.Log;

import com.example.linerapp.app.model.Category;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.ExtendedCompany;
import com.example.linerapp.app.model.Line;
import com.example.linerapp.app.model.LineField;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ильнар on 23.06.2014.
 */
public class JSONLoader {

    static ArrayList<ExtendedCompany> ecompanies = new ArrayList<>();
    static ArrayList<Line> lines = new  ArrayList<>();
    static ArrayList<Category> categories = new ArrayList<>();
    static ArrayList<LineField> lineFields = new ArrayList<>();

    static {
        ecompanies.add(new ExtendedCompany(1, "Итиль", "г. Казань, Шуртыгина ул., д. 24", "Открытое акционерное общество страховая компания “Итиль” зарегистрирована на территории Республики Татарстан 15 февраля 1994 года. Акционером общества является АО “Страховое акционерное общество Армеец” - первая по сборам страховая компания в Республике Болгария (долгосрочный рейтинг надежности на уровне iBBB (стабильный прогноз) и краткосрочный рейтинг ia-3, присвоенный Болгарским агентством по кредитным рейтингам), с долей на рынке общего страхования 12,9% и премий за 2012 год в размере 3,5 млрд. рублей.", 1));
        ecompanies.add(new ExtendedCompany(2, "АльфаСтрахование", "г. Казань, Петербургская ул., д. 42", "Группа «АльфаСтрахование» – одна из крупнейших российских страховых компаний с универсальным портфелем услуг, включающим как комплексные программы защиты интересов бизнеса, так и широкий спектр страховых продуктов для частных лиц. Согласно лицензии, компания предлагает более 100 продуктов, включая продукты по страхованию жизни.", 1));
        ecompanies.add(new ExtendedCompany(3, "Цюрих", "г. Казань, Ямашева пр-кт., д. 36, стр. 1", "Компания со штатом более 55 000 сотрудников предлагает широкий ассортимент продуктов и услуг в области общего страхования и страхования жизни. Клиенты Zurich – как частные лица, так и предприятия малого, среднего и крупного бизнеса, включая международные корпорации, присутствующие более чем в 170 странах. Штаб-квартира Группы, основанной в 1872 г., расположена в г. Цюрих, Швейцария. Акции холдинговой компании Zurich Insurance Group Ltd (ZURN), торгуются на бирже SIX Swiss Exchange. Кроме того, существует программа американских депозитарных расписок 1-го уровня (ZURVY), участвующих в системе внебиржевой торговли OTCQX. Подробную информацию о Zurich можно найти на веб-сайте компании www.zurich.com.", 1));
        ecompanies.add(new ExtendedCompany(4, "Арт Пассаж", "г. Казань, Дениса Давыдова ул., д. 28, стр. 1", "Сеть багетных мастерских \"Арт Пассаж\" работает более 3х лет, мы предоставляем услуги по оформлению любого вида изображений: картина, зеркало, вышивка или объемные предметы.\n" +
                "Главная составляющая успеха любого интерьера - это неповторимый стиль и запоминающиеся детали. Картина является одним из самых важных и значимых элементов декора, наравне с современной версией арт-постера в раме. У нас вы можете выбрать и купить любой, понравившийся вам постер, который мы изготовим по вашим размерам, тщательно и со вкусом подберем к нему оформление. Такие работы привлекут внимание и не оставят гостей равнодушными. В спектр наших услуг входит не только изготовление постеров на заказ, но и профессиональное оформление его в багетную раму.", 2));
        ecompanies.add(new ExtendedCompany(5, "HDSolutions", "г. Казань, Фатыха Амирхана пр-кт., д. 53а", "7 простых правил HD Solutions «Быть Человеком»\n" +
                "Любого плохого человека можно научить быть прекрасным оператором, дизайнером, фотографом и даже врачом. Но почти никогда нельзя научить кого-либо быть хорошим человеком. Наш коллектив – это, в первую очередь, хорошие люди. И мы это ценим. «Быть профессионалами»\n" +
                "Каждая работа, за которую мы беремся, это не только ваш успех, но и наша визитная карточка, поэтому мы не меньше вашего заинтересованы в отличном результате. «Быть в движении»\n" +
                "Любая профессиональная деятельность – подобна езде на велосипеде, остановка означает падение. Мы постоянно развиваемся и улучшаем качество работ, для того, чтобы соответствовать современным требованиям в области видеопродакшена. «Быть проще»\n" +
                "Мы относимся серьезно к работе, а не к себе. «Быть новаторами»\n" +
                "Мы считаем, что лучше разработать что-то новое и оригинальное, нежели выдавать чужие идеи за свои. При этом мы не игнорируем передовой опыт в нашем виде деятельности. «Быть честными»\n" +
                "Мы считаем, что обманув клиента ради сиюминутной выгоды, мы потеряем в разы больше во всех отношениях, поэтому честно ведём наш бизнес и доверяем друг другу. «Быть полезными»\n" +
                "Нам не все равно, в каком обществе жить. Социальное направление наших работ – это посильный вклад в формирование здорового общества в Казани и в стране в целом.", 2));
        ecompanies.add(new ExtendedCompany(6, "YES", "г. Казань, Победы пр-кт., д. 39", "Изучение английского языка\n" +
                "Активное изучение иностранных языков – сегодня не просто дань моде, а жизненная необходимость для построения успешной карьеры, личностного роста и самосовершенствования, расширения своего кругозора. Современный образовательный центр YES приглашает вас записаться на курсы английского языка по инновационным эффективным методикам.", 3));
        ecompanies.add(new ExtendedCompany(7, "Лингва", "Адрес: г. Казань, Чистопольская ул., д. 19а", "\"Лингва\" - курсы английского языка в Казани.\n" +
                "Английские школы в Казани призваны помочь овладеть английским с нуля, или же поднять разговорные навыки до высокого уровня. Курсы английского необходимы, когда дело касается карьеры: человек, знающий английский язык, всегда имеет сто очков вперёд того, кто в своё время поленился изучать его.\n" +
                "Во всех крупных фирмах одно из ключевых требований к кандидату - знание английского языка. И Казань - не исключение из правил, здесь множество фирм, ориентированных на западных клиентов. И успешно работать в подобной фирме, не окончив курсы английского языка, сложно, либо вовсе не предоставляется.\n" +
                "Бывает и так, что трудоустроиться на хорошую, интересную и выгодную должность возможно, но без знания английского выход на должность невозможен, и она достаётся кому-то более предусмотрительному. Тому, кто не поленился и потратил несколько часов в неделю на курсы английского для начинающих, чтобы приобрести начальные навыки языка. А потом закрепил их, пройдя курс разговорного английского. И не пожалел времени на завершающий шаг - бизнес-курс английского языка, который позволил ему овладеть языком на ещё более высоком уровне.", 3));
        ecompanies.add(new ExtendedCompany(8, "5 Власть", "г. Казань, Муштари ул., д. 12Б", "Мы занимаемся созданием и продвижением сайтов по всей России, но наш главный офис находится в Казани. Основатель компании «5 Власть» Алфас Хусам уже в 2007 году начал свою деятельность в области продвижения сайтов в сети Интернет. За эти годы им был накоплен огромный опыт по созданию и продвижению сайтов.\n" +
                "На данный момент наша компания состоит из 10 квалифицированных специалистов, которые готовы создать для Вас новый сайт или же помочь Вашему интернет-проекту занять лидирующие позиции в поисковой выдаче.\n" +
                "«5 Власть» предлагает удобные тарифы на любой вкус по созданию и продвижению сайтов, мы используем в своей деятельности «белые» методы раскрутки и способны сделать для Вас проект любой сложности.", 4));
        ecompanies.add(new ExtendedCompany(9, "Digital Zone", "г. Казань, Петербургская ул., д. 50, стр. 23", "Центральный офис Digital Zone располагается в Москве, основные подразделения разработчиков — в Санкт-Петербурге, Казани, Ульяновске, Краснодаре. Общее количество разработчиков и специалистов по IT — 170 человек.\n" +
                "Методология ведения проектов (жизненный цикл проекта, принятый в компании):\n" +
                "обращение клиента;\n" +
                "анализ задачи, предметной области, конкурентов и прочих артефактов;\n" +
                "подготовка коммерческого предложения, пресейл;\n" +
                "предварительное прототипирование;\n" +
                "подписание документов, старт проекта;\n" +
                "выбор методологии разработки, классической или гибкой;\n" +
                "формирование проектной команды применяя матричную систему;\n" +
                "разработка требований, ТЗ, архитектуры, моделей и прочих документов;\n" +
                "разработка кода;\n" +
                "QA тестирование;\n" +
                "двух недельный цикл формирования релизов для клиента (возможна другая схема);\n" +
                "документирование.\n" +
                "Состав работ может меняться в зависимости от специфики проекта.", 4));
        ecompanies.add(new ExtendedCompany(10, "Business Solutions", "г. Казань, Победы пр-кт., д. 206", "Вы наверняка хоть раз попадали на сайт, с которого долго не хочется уходить. И не только потому, что Вам интересна тематика. А потому что такие сайты грамотно сделаны и наделены силой интернет-продаж!\n" +
                "Такой сайт — это уникальный продавец, который работает на Вас круглосуточно и без устали. Только представьте — в режиме 24/7 и без Вашего участия он встречает потенциальных покупателей, буквально «берет их за руку» и ведет по страницам, эффектно демонстрируя Ваши товары или услуги. В процессе акцентирует внимание на выгодах и преимуществах, отвечает на возникшие вопросы и снимает возражения. Легко, быстро, без каких-либо усилий с Вашей стороны. Мы создаем такие сайты. Хотите?", 4));
        ecompanies.add(new ExtendedCompany(11, "Live in Love", "г. Казань, Николая Ершова ул., д. 1а", "Компания \"Live in Love\" - это творческая, инициативная и креативная команда с множеством идей и огромным желанием воплотить ваши мечты в жизнь. Наше отличие - это высокая требовательность к себе и своей работе. Нас вдохновляют люди с блеском в глазах. Свадьба для нас как произведение искусства, в которой каждая деталь продумана и гармонична. Нам важен каждый наш клиент, поскольку мы понимаем, что вы доверяете нам подготовку самого важного дня вашей жизни. Свадьба - это лишь наше первое знакомство, после которого мы становимся друзьями и сопровождаем вас по жизни)) Мы встречаем ваших малышей с роддома и устраиваем им дни рождения. Мы обожаем ваши искренние улыбки и очень ценим нашу дружбу. Live in Love или жить в любви - чрезвычайно важно для каждого человека и мы готовы помочь вам сделать вашу жизнь прекраснее и счастливее))", 5));
        ecompanies.add(new ExtendedCompany(12, "Dream weDding", "г. Казань, Адоратского ул., д. 53", "Целый мир создан только для Вас, для Тебя и Него, и об этом знаете только Вы вдвоем. Дайте нам возможность убедить в этом все остальных!\n" +
                "«Dream wedding» – свадебное агентство, существующее с 2009 года. За это время мы провели много мероприятий различного уровня, отличавшихся индивидуальностью и неповторимостью. Каждая новая пара – отдельная история любви, требующая внимательного подхода. Нам важны Ваши желания и мы все сделаем для того, чтобы Ваши мечты об идеальной свадьбе сбылись. Наслаждайтесь прекрасным днем с Вашей семьей и друзьями, важными для Вас людьми.", 5));
        ecompanies.add(new ExtendedCompany(13, "АК БАРС БАНК", "г. Казань, Декабристов ул., д. 1", "Банк располагает всеми видами существующих в Российской Федерации банковских лицензий и оказывает более 100 видов банковских услуг для корпоративных и частных клиентов.\n" +
                "Сегодня «АК БАРС» Банк обслуживает более 2,5 миллионов частных лиц и свыше 50 тысячи корпоративных клиентов, среди которых — крупнейшие экспортеры Республики Татарстан, предприятия нефтегазового и нефтехимического комплекса, машиностроительные, телекоммуникационные, строительные, химические, автотранспортные, торговые и агропромышленные предприятия.", 6));
        ecompanies.add(new ExtendedCompany(14, "Хоум Кредит Энд Финанс", "г. Казань, Декабристов ул., д. 187", "Банк Хоум Кредит занимает лидерские позиции на рынке финансовой розницы России. Входит в ТОП-5 по кредитам, срочным вкладам населения и размеру филиальной сети. В этом разделе мы представляем основные факты из истории успешного развития банка на российском рынке.", 1));
        lines.add(new Line(1, "Очередь на прием", "Очередь на прием", ""));
        categories.add(new Category(1, "Страховые компании", ""));
        categories.add(new Category(2, "Фотостудии", ""));
        categories.add(new Category(3, "Изучение иностранных языков", ""));
        categories.add(new Category(4, "Создание сайтов", ""));
        categories.add(new Category(5, "Организация праздников", ""));
        categories.add(new Category(6, "Банки", ""));
        lineFields.add(new LineField(1, "string", "name", "Ваше имя:", null));
        lineFields.add(new LineField(2, "datetime_picker", "datetime", "Желаемая дата и время приема:", null));
        lineFields.add(new LineField(3, "phone", "phone", "Ваш номер телефона:", null));
    }

    private JSONLoader() {
    }

    public static String BASE_URL = "https://linerapp.com/api/v1/";

    /**
     * Returns list of categories downloaded from server
     *
     * @return categories list
     */
    public static ArrayList<Category> loadCategories() {
        String address = BASE_URL.concat("categories/");

        /*JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Category> categories = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                categories.add(new Category(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("description")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        return categories;
    }

    /**
     * Returns list of companies downloaded from server
     *
     * @return companies list
     */
    public static ArrayList<Company> loadAllCompanies() {
        String address = BASE_URL.concat("companies/");

        /*JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Company> companies = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                companies.add(new Company(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("address")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        ArrayList<Company> companyArrayList = new ArrayList<>();
        for (ExtendedCompany company : ecompanies) {
            companyArrayList.add(company);
        }
        return companyArrayList;
    }

    public static List<Company> loadCompaniesByName(String name) {
        /*String address = BASE_URL.concat("companies/search?query=" + name);

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Company> companies = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                companies.add(new Company(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("address")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        List<Company> result = new ArrayList<>();
        for (ExtendedCompany company : ecompanies) {
            if (company.getName().contains(name)) {
                result.add(company);
            }
        }
        return result;
    }

    /**
     * Returns list of companies with given categories downloaded from server
     *
     * @param categories id of categories which must have company
     * @return companies with given categories id list
     */
    public static ArrayList<Company> loadCompaniesWithCategory(Integer[] categories) {

        Set<Company> set = new HashSet<>();

        for (Integer category : categories) {

            String address = BASE_URL.concat("categories/" + category + "/get_companies");

            /*JSONArray jsonArray = null;
            try {
                jsonArray = getJSONFromURL(address);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            if (jsonArray.length() == 0) continue;

            ArrayList<Company> companies = new ArrayList<>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    companies.add(new Company(jsonObject.getInt("id"), jsonObject.getString("name"),
                            jsonObject.getString("address")));
                }
            } catch (JSONException e) {
                Log.e("Error", "Error parsing JSON array");
                e.printStackTrace();
            }*/
            for (ExtendedCompany company : ecompanies) {
                if (company.getCategotyId() == category) {
                    set.add(company);
                }
            }
//            set.addAll(companies);
        }

        return new ArrayList<>(set);
    }

    public static ExtendedCompany loadCompanyById(int id) {

        /*String address = BASE_URL.concat("companies/" + id);

        JSONObject jsonObject = null;
        try {
            jsonObject = getJSONObjectFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ExtendedCompany company = null;
        try {
            company = new ExtendedCompany(jsonObject.getInt("id"), jsonObject.getString("name"),
                    jsonObject.getString("address"));
            company.setDescription(jsonObject.getString("description"));
            company.setShortUrl(jsonObject.getString("short_url"));
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        for (ExtendedCompany ecompany : ecompanies) {
            if (ecompany.getId() == id) {
                return ecompany;
            }
        }
        return null;
//        return company;
    }

    public static ArrayList<Line> getLines(int companyId) {
       /* String address = BASE_URL.concat("companies/" + companyId + "/lines");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<Line> lines = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lines.add(new Line(jsonObject.getInt("id"), jsonObject.getString("name"),
                        jsonObject.getString("description"), jsonObject.getString("short_url")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        return lines;
    }

    public static ArrayList<LineField> getLineFields(int lineId) {
        /*String address = BASE_URL.concat("companies/0/lines/" + lineId + "/line_fields");

        JSONArray jsonArray = null;
        try {
            jsonArray = getJSONFromURL(address);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<LineField> lineFields = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lineFields.add(new LineField(jsonObject.getInt("id"), jsonObject.getString("custom_field_type"),
                        jsonObject.getString("name"), jsonObject.getString("label"), jsonObject.getString("data")));
            }
        } catch (JSONException e) {
            Log.e("Error", "Error parsing JSON array");
            e.printStackTrace();
        }*/
        return lineFields;
    }

    public static void submitForm(String toSubmit, String companyUrl, String lineUrl) throws Exception{
        /*String address = BASE_URL.concat(companyUrl + "/lines/" + lineUrl + "/submit");
        Log.e("URL", address);
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(address);
        StringEntity params = new StringEntity(toSubmit);
        Log.e("JSON", toSubmit);
        request.addHeader("X-Account-Token", "kLqqt7tR7vHTsqPZxRaT");
        request.addHeader("X-Account-Email", "api_user@linerapp.com");
        request.setEntity(params);
        HttpResponse response = client.execute(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder out = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        Log.e("Out", out.toString());
        if (out.toString().equals("Invalid order params")) {
            throw new Exception();
        }*/
    }

    /**
     * Returns JSON array downloaded from given URL
     *
     * @param address url
     * @return downloaded JSON array
     * @throws IOException when downloaded data can't be parsed to JSON array
     */
    public static JSONArray getJSONFromURL(String address) throws IOException {

        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("X-Account-Token", "kLqqt7tR7vHTsqPZxRaT");
        connection.addRequestProperty("X-Account-Email", "api_user@linerapp.com");
        connection.connect();
        InputStream stream = connection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();

        try {
            JSONArray jsonArray = new JSONArray(sb.toString());
            Log.e("data", sb.toString());
            return jsonArray;
        } catch (JSONException e) {
            Log.e("Error parsing data", "\n" + sb.toString() + "\n");
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObjectFromURL(String address) throws IOException {

        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("X-Account-Token", "kLqqt7tR7vHTsqPZxRaT");
        connection.addRequestProperty("X-Account-Email", "api_user@linerapp.com");
        connection.connect();
        InputStream stream = connection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line + "\n");
        }
        stream.close();

        try {
            JSONObject jsonObject = new JSONObject(sb.toString());
            Log.e("data", sb.toString());
            return jsonObject;
        } catch (JSONException e) {
            Log.e("Error parsing data", "\n" + sb.toString() + "\n");
            e.printStackTrace();
        }
        return null;
    }
}
