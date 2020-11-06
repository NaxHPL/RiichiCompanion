package com.example.mahjonghelper;

import android.util.Pair;

import java.util.EnumMap;

public class Scoring {

    private static final EnumMap<Han, EnumMap<Fu, ScoreEntry>> table = new EnumMap<Han, EnumMap<Fu, ScoreEntry>>(Han.class) {{
        ScoreEntry manganEntry = new ScoreEntry(12000, 4000, 8000, Pair.create(2000, 4000));
        ScoreEntry hanemanEntry = new ScoreEntry(18000, 6000, 12000, Pair.create(3000, 6000));
        ScoreEntry baimanEntry = new ScoreEntry(24000, 8000, 16000, Pair.create(4000, 8000));
        ScoreEntry sanbaimanEntry = new ScoreEntry(36000, 12000, 24000, Pair.create(6000, 12000));
        ScoreEntry yakumanEntry = new ScoreEntry(48000, 16000, 32000, Pair.create(8000, 16000));

        put(Han.OneHan, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            put(Fu.Thirty, new ScoreEntry(1500, 500, 1000, Pair.create(300, 500)));
            put(Fu.Forty, new ScoreEntry(2000, 700, 1300, Pair.create(400, 700)));
            put(Fu.Fifty, new ScoreEntry(2400, 800, 1600, Pair.create(400, 800)));
            put(Fu.Sixty, new ScoreEntry(2900, 1000, 2000, Pair.create(500, 1000)));
            put(Fu.Seventy, new ScoreEntry(3400, 1200, 2300, Pair.create(600, 1200)));
            put(Fu.Eighty, new ScoreEntry(3900, 1300, 2600, Pair.create(700, 1300)));
            put(Fu.Ninety, new ScoreEntry(4400, 1500, 2900, Pair.create(800, 1500)));
            put(Fu.Hundred, new ScoreEntry(4800, 1600, 3200, Pair.create(800, 1600)));
            put(Fu.HundredTen, new ScoreEntry(5300, 1800, 3600, Pair.create(900, 1800)));
        }});
        put(Han.TwoHan, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            put(Fu.Twenty, new ScoreEntry(2000, 700, 1300, Pair.create(400, 700)));
            put(Fu.TwentyFive, new ScoreEntry(2400, 0, 1600, Pair.create(0, 0)));
            put(Fu.Thirty, new ScoreEntry(2900, 1000, 2000, Pair.create(500, 1000)));
            put(Fu.Forty, new ScoreEntry(3900, 1300, 2600, Pair.create(700, 1300)));
            put(Fu.Fifty, new ScoreEntry(4800, 1600, 3200, Pair.create(800, 1600)));
            put(Fu.Sixty, new ScoreEntry(5800, 2000, 3900, Pair.create(1000, 2000)));
            put(Fu.Seventy, new ScoreEntry(6800, 2300, 4500, Pair.create(1200, 2300)));
            put(Fu.Eighty, new ScoreEntry(7700, 2600, 5200, Pair.create(1300, 2600)));
            put(Fu.Ninety, new ScoreEntry(8700, 2900, 5800, Pair.create(1500, 2900)));
            put(Fu.Hundred, new ScoreEntry(9600, 3200, 6400, Pair.create(1600, 3200)));
            put(Fu.HundredTen, new ScoreEntry(10600, 3600, 7100, Pair.create(1800, 3600)));
        }});
        put(Han.ThreeHan, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            put(Fu.Twenty, new ScoreEntry(3900, 1300, 2600, Pair.create(700, 1300)));
            put(Fu.TwentyFive, new ScoreEntry(4800, 1600, 3200, Pair.create(800, 1600)));
            put(Fu.Thirty, new ScoreEntry(5800, 2000, 3900, Pair.create(1000, 2000)));
            put(Fu.Forty, new ScoreEntry(7700, 2600, 5200, Pair.create(1300, 2600)));
            put(Fu.Fifty, new ScoreEntry(9600, 3200, 6400, Pair.create(1600, 3200)));
            put(Fu.Sixty, new ScoreEntry(11600, 3900, 7700, Pair.create(2000, 3900)));

            for (Fu fu : Fu.values()) {
                if (fu.ordinal() > 5)
                    put(fu, manganEntry);
            }
        }});
        put(Han.FourHan, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            put(Fu.Twenty, new ScoreEntry(7700, 2600, 5200, Pair.create(1300, 2600)));
            put(Fu.TwentyFive, new ScoreEntry(9600, 3200, 6400, Pair.create(1600, 3200)));
            put(Fu.Thirty, new ScoreEntry(11600, 3900, 7700, Pair.create(2000, 3900)));

            for (Fu fu : Fu.values()) {
                if (fu.ordinal() > 2)
                    put(fu, manganEntry);
            }
        }});
        put(Han.Mangan, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            for (Fu fu : Fu.values())
                put(fu, manganEntry);
        }});
        put(Han.Haneman, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            for (Fu fu : Fu.values())
                put(fu, hanemanEntry);
        }});
        put(Han.Baiman, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            for (Fu fu : Fu.values())
                put(fu, baimanEntry);
        }});
        put(Han.Sanbaiman, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            for (Fu fu : Fu.values())
                put(fu, sanbaimanEntry);
        }});
        put(Han.Yakuman, new EnumMap<Fu, ScoreEntry>(Fu.class) {{
            for (Fu fu : Fu.values())
                put(fu, yakumanEntry);
        }});
    }};

    public static ScoreEntry getScoreEntry(Han han, Fu fu) {
        return table.get(han).get(fu);
    }

    public static ScoreEntry getScoreEntry(int han, int fu) {
        return getScoreEntry(hanToEnum(han), fuToEnum(fu));
    }

    public static Han hanToEnum(int han) {
        if (han <= 1)
            return Han.OneHan;
        if (han == 2)
            return Han.TwoHan;
        if (han == 3)
            return Han.ThreeHan;
        if (han == 4)
            return Han.FourHan;
        if (han == 5)
            return Han.Mangan;
        if (han <= 7)
            return Han.Haneman;
        if (han <= 10)
            return Han.Baiman;
        if (han <= 12)
            return Han.Sanbaiman;

        return Han.Yakuman;
    }

    public static Fu fuToEnum(int fu) {
        if (fu <= 20)
            return Fu.Twenty;
        if (fu == 25)
            return Fu.TwentyFive;
        if (fu <= 30)
            return Fu.Thirty;
        if (fu <= 40)
            return Fu.Forty;
        if (fu <= 50)
            return Fu.Fifty;
        if (fu <= 60)
            return Fu.Sixty;
        if (fu <= 70)
            return Fu.Seventy;
        if (fu <= 80)
            return Fu.Eighty;
        if (fu <= 90)
            return Fu.Ninety;
        if (fu <= 100)
            return Fu.Hundred;

        return Fu.HundredTen;
    }
}
