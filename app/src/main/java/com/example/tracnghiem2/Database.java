package com.example.tracnghiem2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tracnghiem2.adapter.QuestionAdapter;
import com.example.tracnghiem2.model.Category;
import com.example.tracnghiem2.model.Question;
import com.example.tracnghiem2.model.Score;
import com.example.tracnghiem2.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "TracNghiem";
    private static int VERSION = 1;
    // bảng users
    private static String TABLE_USERS = "users";
    private static String ID_USER = "id";
    private static String NAME_USER = "name";
    private static String PASSWORD = "password";
    private static String ROLE = "role";

    // bảng categories
    private static String TABLE_CATEGORIES = "categories";
    private static String ID_CATEGORY = "id";
    private static String NAME_CATEGORY = "name";
    // bảng question
    private static String TABLE_QUESTIONS = "questions";
    private static String ID_QUESTION = "id";
    private static String NAME_QUESTION = "question";
    private static String OPTION1 = "option1";
    private static String OPTION2 = "option2";
    private static String OPTION3 = "option3";
    private static String OPTION4 = "option4";
    private static String ANSWER = "answer";
    private static String ID_CATEGORY_FK = "id_category";

    // bảng score
    private static String TABLE_SCORES = "scores";
    private static String ID_SCORE = "id";
    private static String ID_USER_FK = "id_user";
    private static String ID_CATEGORY_FK_2 = "id_category";
    private static String SCORE = "score";
    private static String DATE = "create_at";

    private Context context;

    private SQLiteDatabase db;


    //tạo bảng users
    private String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "( " + ID_USER
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_USER + " TEXT UNIQUE, " + PASSWORD + " TEXT, " + ROLE + " INTEGER)";

    // tạo bảng categories
    private String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "( "
            + ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_CATEGORY + " TEXT " + ")";

    // tạo bảng questions
    private String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + " ( "
            + ID_QUESTION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_QUESTION + " TEXT, "
            + OPTION1 + " TEXT, " + OPTION2 + " TEXT, " + OPTION3 + " TEXT, " + OPTION4 + " TEXT, "
            + ANSWER + " INTEGER, " + ID_CATEGORY_FK + " INTEGER , FOREIGN KEY ( " + ID_CATEGORY_FK
            + " ) REFERENCES " + TABLE_CATEGORIES + "(" + ID_CATEGORY + ") ON DELETE CASCADE)";

    // tạo bảng scores
    private String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + " ( " + ID_SCORE
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_USER_FK + " INTEGER, " + ID_CATEGORY_FK_2
            + " INTEGER, " + SCORE + " INTEGER, " + DATE + " TEXT DEFAULT (strftime('%d-%m-%Y, %H:%M','now', 'localtime')), "
            + " FOREIGN KEY ( " + ID_USER_FK + " ) REFERENCES " + TABLE_USERS + "(" + ID_USER + ") ON DELETE CASCADE, "
            + "FOREIGN KEY ( " + ID_CATEGORY_FK_2 + " ) REFERENCES "
            + TABLE_CATEGORIES + "(" + ID_CATEGORY_FK_2 + ") ON DELETE CASCADE)";


    // dữ liệu users
    private String insertUser = "INSERT INTO users VAlUES (null,'admin', '1', 1), (null,'khai', '1', 0)";

    // chèn dữ liệu categories
    private String insertCate = "INSERT INTO categories VAlUES (null,'Toán'), (null,'Văn'), (null,'Anh')";

    // chèn dữ liệu questions
    private String insertQues = "INSERT INTO questions VAlUES (null, 'Một cân sắt và một cân bông cái nào nặng hơn?', 'A. Sắt', 'B. Bông', 'C. Bằng nhau', 'D. A hoặc B', 3, 1)," +
            " (null, '10^6 - 10^5 + 800 = ?', 'A. 800 - 10^5 + 10^6', 'B. 900800', 'C. 10^6', 'D. ?', 4, 1)," +
            "(null, 'Diện tích hình tam giác có chiều cao h và đáy d bằng ?', 'A. d*h/2', 'B. d*h', 'C. (d+h)/2', 'D. ?', 1, 1)," +
            "(null, '1 + 1 = ?', 'A. 1', 'B. 2', 'C. 3', 'D. 4', 2, 1)," +
            "(null, 'Cho ba vecto a, b, c. Điều kiện nào sau đây không kết luận được ba vecto đó đồng phẳng.'," +
            " 'A. Một trong ba vecto đó bằng 0.', 'B. Có hai trong ba vecto đó cùng phương.', " +
            "'C. Có một vecto không cùng hướng với hai vecto còn lại', 'D. Có hai trong ba vecto đó cùng hướng.', 2, 1)," +
            "(null, 'Trong các khẳng định sau; khẳng định nào đúng?" +
            " ', 'A. Qua hai điểm phân biệt có duy nhất một mặt phẳng.'," +
            " 'B. Qua ba điểm phân biệt bất kì có duy nhất một mặt phẳng.'," +
            " 'C. Qua ba điểm không thẳng hàng có duy nhất một mặt phẳng.', " +
            "'D. Qua bốn điểm bất kì có duy nhất một mặt phẳng.', 3, 1)," +
            "(null, 'Trong không gian; cho 5 điểm không đồng phẳng. Có thể xác định được tối đa bao nhiêu mặt phẳng phân biệt từ các điểm đã cho?'," +
            " 'A. 7', 'B. 8', 'C. 10', 'D. 4', 3, 1)," +
            "(null, 'Trong các hình chóp, hình chóp có ít cạnh nhất có số cạnh là bao nhiêu?', 'A. 3', 'B. 4', 'C. 5', 'D. 6', 4, 1)," +
            "(null, 'Huy năm nay 10 tuổi, chỉ Huy gấp đôi tuổi Huy. Hỏi 10 năm sau chị Huy bao nhiêu tuổi?', 'A. 40', 'B. 30', 'C. 50', 'D. 45', 2, 1)," +
            "(null, 'Một cầu vồng có 7 màu, vậy 3 cầu vồng có?', 'A. 7', 'B. 14', 'C. 21', 'D. 28', 1, 1)," +
            "(null, 'Một con ngựa đau cả tàu _____?', 'A. bỏ cỏ', 'B. bỏ đi', 'C. mặc kệ', 'D. bỏ nhỏ', 1, 2)," +
            "(null, 'Thẳng như _____?', 'A. thước kẻ', 'B. bóng đèn', 'C. ruột ngựa', 'D. ruột lợn', 3, 2)," +
            "(null, 'Nguyễn Duy Mạnh là?', 'A. Nhà thơ', 'B. Thợ hát', 'C. Thợ xây', 'D. Thợ mộc', 2, 2)," +
            "(null, 'Ai là tác giả bài thơ Đồng chí?', 'A. Tố Hữu', 'B. Nguyễn Duy Mạnh', 'C. Huy Cận', 'D. Chế Lan Viên', 1, 2)," +
            "(null, 'Đại Việt sử ký toàn thư do ai sáng tác?', 'A. Trần Hưng Đạo', 'B. Ngô Sĩ Liên', 'C. Trần Thủ Độ', 'D. Trần Khánh Dư', 2, 2)," +
            "(null, 'Bài thơ Lượm được sàng tác năm bao nhiêu?', 'A. 1949', 'B. 1950', 'C. 1961', 'D. 1962', 1, 2)," +
            "(null, 'Tác phẩm Số đỏ do ai viết?', 'A. Tố Hữu', 'B. Vũ Trọng Phụng', 'C. Xuân Diệu', 'D. Nam Cao', 2, 2)," +
            "(null, 'Cậu Vàng xuất hiện trong tác phẩm nào?', 'A. Đời thừa', 'B. Lão Hạc', 'C. Quê tôi', 'D. Con chó', 2, 2)," +
            "(null, 'Bà huyện Thanh Quan là tác giả của?', 'A. Quanh co', 'B. Dốc đứng', 'C. Đèo dọc', 'D. Đèo ngang', 4, 2)," +
            "(null, 'Ai là tác giả bài thơ Đoàn thuyền đánh cá?', 'A. Tố Hữu', 'B. Huy Cận', 'C. Xuân Diệu', 'D. Chế Lan Viên', 2, 2)," +
            "(null, 'Who are all ________ people??', 'A. this', 'B. those', 'C. them', 'D. that', 2, 3)," +
            "(null, 'Claude is ________.', 'A. Frenchman', 'B. a French', 'C. a Frenchman', 'D. French man', 2, 3)," +
            "(null, 'They are all ________ ready for the party.', 'A. getting', 'B. going', 'C. doing', 'D. putting', 1, 3)," +
            "(null, 'Jane _____ as a fashion designer for ten years before becoming a famous singer.', 'A. worked', 'B. is working', 'C. works', 'D. will work', 1, 3)," +
            "(null, 'Dan can _____ the drum very well.', 'A. play', 'B. do', 'C. make', 'D. think', 1, 3)," +
            "(null, 'My friend is ______ so she has a lot of free time.', 'A. singer', 'B. married', 'C. single', 'D. free', 3, 3)," +
            "(null, 'I know somebody ________ can play the guitar.', 'A. he', 'B. who', 'C. what', 'D. that he', 2, 3)," +
            "(null, 'Did you ask your father ________ some money?', 'A. 0', 'B. after', 'C. on', 'D. for', 4, 3)," +
            "(null, 'Those students are working very _____ for their next exams.', 'A. hardly', 'B. hard', 'C. harder', 'D. hardest', 2, 3)," +
            "(null, ' When do you go ________ bed?', 'A. to', 'B. to the', 'C. in', 'D. on', 1, 3)";

    // chèn dữ liệu categories
    private String insertScore = "INSERT INTO scores(id_user, id_category, score) VAlUES (2, 1, 230)," +
            "(1, 2, 240), (1, 2, 250),(2, 1, 260)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_SCORES_TABLE);
        //chèn dữ liệu
        db.execSQL(insertUser);
        //
        db.execSQL(insertCate);
        //
        db.execSQL(insertQues);
        //
        db.execSQL(insertScore);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    //CATEGORIES
    public boolean addCategory(Category category) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            //insert thông qua contentvalues
            ContentValues values = new ContentValues();
            values.put(NAME_CATEGORY, category.getName());

            db.insert(TABLE_CATEGORIES, null, values);

            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateCategory(Category category) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_CATEGORY, category.getName());

            int rs = db.update(TABLE_CATEGORIES, values, ID_CATEGORY + " = "
                    + category.getId(), null);
            if (rs == 0)
                return false;
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteCategory(int id_category) {
        SQLiteDatabase db = this.getReadableDatabase();
        int rs = db.delete(TABLE_CATEGORIES, ID_CATEGORY + "=" + id_category, null);
        if (rs == 0)
            return false;
        return true;
    }

    public Category getCategoryById(int id) {
        Category category = new Category();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES + " WHERE "
                + ID_CATEGORY + " = " + id, null);
        if (c.moveToFirst()) {
            do {
                category.setId(c.getInt(0));
                category.setName(c.getString(1));
            }
            while (c.moveToNext());
        }
        c.close();
        return category;
    }

    public ArrayList<Category> getListCategories(String key) {
        ArrayList<Category> listCategories = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c;
        if (key == null) {
            c = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);
        } else c = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + ID_CATEGORY
                + " LIKE '%" + key + "%' OR " + NAME_CATEGORY
                + " LIKE '%" + key + "%'", null);
        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(0));
                category.setName(c.getString(1));
                listCategories.add(category);
            }
            while (c.moveToNext());
        }
        c.close();
        return listCategories;
    }

    //QUESTIONS
    public boolean updateQuestion(Question q) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME_QUESTION, q.getQuestion());
            values.put(OPTION1, q.getOption1());
            values.put(OPTION2, q.getOption2());
            values.put(OPTION3, q.getOption3());
            values.put(OPTION4, q.getOption4());
            values.put(ANSWER, q.getAnswer());
            values.put(ID_CATEGORY_FK, q.getId_category());

            int rs = db.update(TABLE_QUESTIONS, values, ID_QUESTION + " = "
                    + q.getId(), null);
            if (rs == 0)
                return false;
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean addQuestion(Question q) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            //insert thông qua contentvalues
            ContentValues values = new ContentValues();
            values.put(NAME_QUESTION, q.getQuestion());
            values.put(OPTION1, q.getOption1());
            values.put(OPTION2, q.getOption2());
            values.put(OPTION3, q.getOption3());
            values.put(OPTION4, q.getOption4());
            values.put(ANSWER, q.getAnswer());
            values.put(ID_CATEGORY_FK, q.getId_category());

            db.insert(TABLE_QUESTIONS, null, values);

            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteQuestion(int id_question) {
        SQLiteDatabase db = this.getReadableDatabase();
        int rs = db.delete(TABLE_QUESTIONS, ID_QUESTION + "=" + id_question, null);
        if (rs == 0)
            return false;
        return true;
    }

    public Question getQuestionById(int id) {
        Question question = new Question();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " WHERE " + ID_QUESTION
                + " = " + id, null);
        if (c.moveToFirst()) {
            do {
                question.setId(c.getInt(0));
                question.setQuestion(c.getString(1));
                question.setOption1(c.getString(2));
                question.setOption2(c.getString(3));
                question.setOption3(c.getString(4));
                question.setOption4(c.getString(5));
                question.setAnswer(c.getInt(6));
                question.setId_category(c.getInt(7));
            }
            while (c.moveToNext());
        }
        c.close();
        return question;
    }

    public ArrayList<Question> getListQuestions(String name, int categoryID) {
        ArrayList<Question> listQuestions = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c;
        if (name == null) {
            c = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " WHERE "
                    + ID_CATEGORY_FK + " = " + categoryID, null);
        } else c = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " WHERE "
                + ID_CATEGORY_FK + " = " + categoryID + " AND " + NAME_QUESTION +
                " LIKE '%" + name + "%'", null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(0));
                question.setQuestion(c.getString(1));
                question.setOption1(c.getString(2));
                question.setOption2(c.getString(3));
                question.setOption3(c.getString(4));
                question.setOption4(c.getString(5));
                question.setAnswer(c.getInt(6));
                question.setId_category(c.getInt(7));
                listQuestions.add(question);
            }
            while (c.moveToNext());
        }
        c.close();
        return listQuestions;
    }

    //USERS

    public ArrayList<User> getListUsers(String key) {
        ArrayList<User> listUsers = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c;
        if (key == null) {
            c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        } else c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + ID_USER +
                " LIKE '%" + key + "%' OR " + NAME_USER + " LIKE '%" + key + "%'", null);
        if (c.moveToFirst()) {
            do {
                User user = new User();
                user.setId(c.getInt(0));
                user.setName(c.getString(1));
                user.setPassword(c.getString(2));
                user.setRole(c.getInt(3));
                listUsers.add(user);
            }
            while (c.moveToNext());
        }
        c.close();
        return listUsers;
    }

    public User getUserByName(String name) {
        User user = new User();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + NAME_USER + " = '"
                + name + "'", null);
        if (c.moveToFirst()) {
            do {
                user.setId(c.getInt(0));
                user.setName(c.getString(1));
                user.setPassword(c.getString(2));
                user.setRole(c.getInt(3));
            }
            while (c.moveToNext());
        }
        c.close();
        return user;
    }

    public User getUserById(int id) {
        User user = new User();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + ID_USER + " = "
                + id, null);
        if (c.moveToFirst()) {
            do {
                user.setId(c.getInt(0));
                user.setName(c.getString(1));
                user.setPassword(c.getString(2));
                user.setRole(c.getInt(3));
            }
            while (c.moveToNext());
        }
        c.close();
        return user;
    }

    public User getUserByNameAndPass(String name, String pass) {
        User user = new User();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + NAME_USER + " = '"
                + name + "' AND " + PASSWORD + " = '" + pass + "'", null);
        if (c.moveToFirst()) {
            do {
                user.setId(c.getInt(0));
                user.setName(c.getString(1));
                user.setPassword(c.getString(2));
                user.setRole(c.getInt(3));
            }
            while (c.moveToNext());
        }
        c.close();

        return user;
    }

    public boolean signUp(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //insert thông qua contentvalues
            ContentValues values = new ContentValues();
            values.put(NAME_USER, user.getName());
            values.put(PASSWORD, user.getPassword());
            values.put(ROLE, user.getRole());

            db.insert(TABLE_USERS, null, values);

            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateUser(User user) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PASSWORD, user.getPassword());
            values.put(ROLE, user.getRole());
            int rs = db.update(TABLE_USERS, values, NAME_USER + " = '"
                    + user.getName() + "'", null);
            if (rs == 0)
                return false;
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean changePass(String user_name, String pass) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PASSWORD, pass);
            int rs = db.update(TABLE_USERS, values, NAME_USER + " = '"
                    + user_name + "'", null);
            if (rs == 0)
                return false;
            db.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean deleteUser(int id_user) {
        SQLiteDatabase db = this.getReadableDatabase();
        int rs = db.delete(TABLE_USERS, ID_USER + "=" + id_user, null);
        if (rs == 0)
            return false;
        return true;
    }

    //SCORE
    public ArrayList<Score> getListScore(int id_category, String user_name) {
        ArrayList<Score> listScore = new ArrayList<>();
        db = getReadableDatabase();
        String getList = "";
        if (user_name != null) {
            getList = "SELECT * FROM " + TABLE_SCORES + " WHERE " + ID_CATEGORY_FK_2 + " = "
                    + id_category + " AND " + ID_USER_FK + " = " + getUserByName(user_name).getId()
                    + " ORDER BY " + ID_SCORE + " DESC LIMIT 10";
        } else
            getList = "SELECT * FROM " + TABLE_SCORES + " WHERE " + ID_CATEGORY_FK_2 +
                    " = " + id_category + " ORDER BY " + SCORE + " DESC LIMIT 10";

        Cursor c = db.rawQuery(getList, null);
        if (c.moveToFirst()) {
            do {
                Score s = new Score();
                s.setId(c.getInt(0));
                s.setId_user(c.getInt(1));
                s.setId_category(c.getInt(2));
                s.setScore(c.getInt(3));
                s.setDate(c.getString(4));
                listScore.add(s);
            }
            while (c.moveToNext());
        }
        c.close();
        return listScore;
    }

    public void createScore(String user_name, int id_category, int score) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //insert thông qua contentvalues
            ContentValues values = new ContentValues();
            values.put(ID_USER_FK, getUserByName(user_name).getId());
            values.put(ID_CATEGORY_FK_2, id_category);
            values.put(SCORE, score);
            db.insert(TABLE_SCORES, null, values);
            db.close();
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
    }

    public int getHighScoreByIdCategory(int id_category) {
        db = getReadableDatabase();
        int highScore = 0;
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SCORES + " WHERE " + ID_CATEGORY_FK_2 +
                " = " + id_category + " ORDER BY " + SCORE + " DESC LIMIT 1", null);
        if (c.moveToFirst()) {
            do {
                highScore = c.getInt(3);
            }
            while (c.moveToNext());
        }
        c.close();
        return highScore;
    }

}
