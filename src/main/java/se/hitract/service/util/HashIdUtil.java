package se.hitract.service.util;

import org.hashids.Hashids;

public class HashIdUtil {

    private static final Hashids HASHIDS = new Hashids("lalalahitRACT", 8);

    private static final Hashids HASHIDS_LONG = new Hashids("lalalahitRACTlong", 20);

    public static String encode(Long source) {
        return HASHIDS.encode(source);
    }

    public static Long decode(String source) {
        return HASHIDS.decode(source)[0];
    }

    public static String encodeLong(Long source) {
        return HASHIDS_LONG.encode(source);
    }

    public static Long decodeLong(String source) {
        return HASHIDS_LONG.decode(source)[0];
    }
}
