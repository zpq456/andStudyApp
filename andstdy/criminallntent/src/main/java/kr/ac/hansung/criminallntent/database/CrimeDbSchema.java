package kr.ac.hansung.criminallntent.database;

/**
 * Created by Owner on 2016-07-23.
 */
public class CrimeDbSchema {//DB데이터 모음 클래스
    public static final class CrimeTable{
        public static final String NAME = "crimes";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
