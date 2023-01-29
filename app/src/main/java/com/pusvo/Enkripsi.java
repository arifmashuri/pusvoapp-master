package com.pusvo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.ClipboardManager;
import android.text.format.DateFormat;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Enkripsi {
    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String access_key(final String randomUUIDString) {
        String alay=randomUUIDString+"its beautiful day but someone open my sourcecode oh please im just a joblessman";
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(alay.getBytes("UTF-8"),
                    0, alay.length());
            byte[] sha1hash = md.digest();

            return toHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String api_secret(final String api_key,final String username) {
        String alay2=api_key+"maaf penghasilan saya masih kecil tolong jangan disalahgunakan"+username;
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(alay2.getBytes("UTF-8"),
                    0, alay2.length());
            byte[] sha1hash = md.digest();

            return toHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(final byte[] buf) {
        if (buf == null) return "";

        int l = buf.length;
        StringBuffer result = new StringBuffer(2 * l);

        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789abcdef";

    private static void appendHex(final StringBuffer sb, final byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f))
                .append(HEX.charAt(b & 0x0f));
    }


    public static String apikey(final String text) {

        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("UTF-8"),
                    0, text.length());
            byte[] sha1hash = md.digest();

            return toHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        return "Rp"+ NumberFormat.getNumberInstance(localeID).format(number);
    }
    public static String idkrupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        return NumberFormat.getNumberInstance(localeID).format(number)+" IDK";
    }
    public static String idk(Double number) {
        Locale localeID = new Locale("in", "ID");
        return NumberFormat.getNumberInstance(localeID).format(number);
    }
    public static Double hargafixbaru(Double harga) {
        Double hargah = harga * 101/100;

        String harganya = "";

harganya= hargah.toString().split("\\.")[0]; ;
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return Double.valueOf(baku2 + 100);
            } else {
                return Double.valueOf(baku2);
            }

        } else {
            return hargah;
        }
    }




    public static Double hargashopeefixbaru(Double harga) {
        Double hargah = harga * 1.015;

        String harganya = "";

        harganya= hargah.toString().split("\\.")[0];
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return Double.valueOf(baku2 + 100);
            } else {
                return Double.valueOf(baku2);
            }

        } else {
            return Double.parseDouble(harganya);
        }
    }

    public static Double hargalinkajafixbaru(Double harga) {
        Double hargah = harga * 1.0165;

        String harganya = "";

        harganya= hargah.toString().split("\\.")[0];
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return Double.valueOf(baku2 + 100);
            } else {
                return Double.valueOf(baku2);
            }

        } else {
            return Double.parseDouble(harganya);
            //return harganya;
        }
    }

    public static String vapps(Context context) {
            PackageInfo info = null;

        try {
        info = context.getPackageManager().getPackageInfo(
                context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "gagal";
        }


    }



    public static int hargafix(int harga) {
        int hargah = harga * 101 / 100;

        String harganya = "";
        //String harganya=Float.toString((float) (int) hargah);
        String correctstring[] = Integer.toString(hargah).split(".");
        if (Integer.toString(hargah).contains("."))
            harganya = correctstring[0];
        else
            harganya = Integer.toString(hargah);
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return baku2 + 100;
            } else {
                return baku2;
            }

        } else {
            return hargah;
        }
    }

    public static Double hargafixtopupbaru(Double harga) {
        Double hargah = harga * 1002/1000;

        String harganya = "";

        harganya= hargah.toString().split("\\.")[0]; ;
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return Double.valueOf(baku2 + 100);
            } else {
                return Double.valueOf(baku2);
            }

        } else {
            return hargah;
        }
    }


    public static int hargafixtopup(int harga) {
        int hargah = harga*1002/1000;

        String harganya = "";
        //String harganya=Float.toString((float) (int) hargah);
        String correctstring[] = Integer.toString(hargah).split(".");
        if (Integer.toString(hargah).contains("."))
            harganya = correctstring[0];
        else
            harganya = Integer.toString(hargah);
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return baku2 + 100;
            } else {
                return baku2;
            }

        } else {
            return hargah;
        }
    }



    public static int hargashopeefix2(int harga) {
        double hargah = harga * 1.015;
        String harganya = "";
        String correctstring[] = Double.toString(hargah).split("\\.");
        if (Double.toString(hargah).contains("."))
            harganya = correctstring[0];
        else
            harganya = Double.toString(hargah);
        return harga;
    }
    public static int hargashopeefix(int harga) {
        //double dbl=101.5;
        double hargah = harga * 1.015;


        String harganya = "";
        //String harganya=Float.toString((float) (int) hargah);
        String correctstring[] = Double.toString(hargah).split("\\.");
        if (Double.toString(hargah).contains("."))
            harganya = correctstring[0];
        else
            harganya = Double.toString(hargah);
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return baku2 + 100;
            } else {
                return baku2;
            }

        } else {
            return Integer.parseInt(harganya);
        }
    }
public static Double tentukanharga(Double harga,Integer metode,Double rand) {
        if(harga!=null) {
            if (metode.equals(8) || metode.equals(4) || metode.equals(7) || metode.equals(5) || metode.equals(9))
                return harga;
            if (metode.equals(3) || metode.equals(10) || metode.equals(15))
            //return Double.parseDouble(hargafix(Integer.parseInt(harga).toString()));
                return hargafixbaru(harga);
            if (metode.equals(17) || metode.equals(18))
                return hargafixbaru(harga)+rand;
            if (metode.equals(13) || metode.equals(14))
                return (hargafixbaru(harga)) / 1000;
            if (metode.equals(11))
                return hargashopeefixbaru(harga);
            if (metode.equals(12))
                return hargalinkajafixbaru(harga);
        }
    return 0.0;
}

    public static Double tentukanhargaadapter(Double harga,Integer metode,Double rand) {
        if(harga!=null) {
            if (metode.equals(8) || metode.equals(4) || metode.equals(7) || metode.equals(5) || metode.equals(9))
                return harga;

            if (metode.equals(17) || metode.equals(18))
                return hargafixbaru(harga)+rand;
            if (metode.equals(3) || metode.equals(10) || metode.equals(15) || metode.equals(13) || metode.equals(14))
                return hargafixbaru(harga);
            if (metode.equals(11))
                return hargashopeefixbaru(harga);
            if (metode.equals(12))
                return hargalinkajafixbaru(harga);
        }
        return 0.0;
    }

    public static Double tentukanhargaadaptertopup(Double harga,Integer metode,Double rand) {
        if(harga!=null) {
            if (metode.equals(17) || metode.equals(18))
                return hargafixtopupbaru(harga)+rand;
            if (metode.equals(13) || metode.equals(14) || metode.equals(15))
                return hargafixtopupbaru(harga);
        }
        return 0.0;
    }


    public static Double tentukanhargatopup(Double harga,Integer metode,Double rand) {
        if(harga!=null) {
            if (metode.equals(15))
                return hargafixtopupbaru(harga);
            if (metode.equals(17) || metode.equals(18))
                return hargafixtopupbaru(harga)+rand;
            if (metode.equals(13) || metode.equals(14))
                return (hargafixtopupbaru(harga)) / 1000;

        }
        return 0.0;
    }


    public static String tentukanhargatopuprupiah(Double harga,Integer metode,Double rand) {
        if(harga!=null) {

            if (metode.equals(15))
                //return Double.parseDouble(hargafix(Integer.parseInt(harga).toString()));
                return rupiah(hargafixtopupbaru(harga));
            if (metode.equals(17) || metode.equals(18))
                return rupiah(hargafixtopupbaru(harga)+rand);
            if (metode.equals(13) || metode.equals(14))
                return idkrupiah(hargafixtopupbaru(harga)/1000);

        }
        return "Rp 0";
    }

    public static String tentukanhargarupiah(Double harga,Integer metode,Double rand) {
        if(harga!=null) {
            if (metode.equals(8) || metode.equals(4) || metode.equals(7) || metode.equals(5) || metode.equals(9))
                return rupiah(harga);
            if (metode.equals(3) || metode.equals(10) || metode.equals(15))
                //return Double.parseDouble(hargafix(Integer.parseInt(harga).toString()));
            return rupiah(hargafixbaru(harga));
            if (metode.equals(17) || metode.equals(18))
                return rupiah(hargafixbaru(harga)+rand);
            if (metode.equals(13) || metode.equals(14))
//                return (hargafixbaru(harga)) / 1000;
            return idkrupiah(hargafixbaru(harga)/1000);
            if (metode.equals(11))
                return rupiah(hargashopeefixbaru(harga));
            if (metode.equals(12))
                //return hargalinkajafix(Integer.parseInt(harga));
            return rupiah(hargalinkajafixbaru(harga));
        }
        return "Rp 0";
    }
    public static int hargalinkajafix(int harga) {
        //double dbl=101.5;
        double hargah = harga * 1.0165;


        String harganya = "";
        //String harganya=Float.toString((float) (int) hargah);
        String correctstring[] = Double.toString(hargah).split("\\.");
        if (Double.toString(hargah).contains("."))
            harganya = correctstring[0];
        else
            harganya = Double.toString(hargah);
        int hargalength = harganya.length();
        int sub1 = Integer.parseInt(harganya.substring(hargalength - 2, hargalength));
        int sub2 = Integer.parseInt(harganya.substring(0, hargalength - 2));
        String baku = sub2 + "00";
        int baku2 = Integer.parseInt(baku);
        if (harga >= 12000) {
            if (sub1 >= 51) {
                return baku2 + 100;
            } else {
                return baku2;
            }

        } else {
            return Integer.parseInt(harganya);
        }
    }


    public void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("" +
                    " telah dicopy", text);
            clipboard.setPrimaryClip(clip);
        }
    }
    public static DecimalFormat decimalformat = new DecimalFormat("####.#");

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        return date;
    }

    public static boolean validasihp(String hp) {
        if(hp.length()>=5) {
            String nomor = hp.substring(0, 4);
            return nomor.equals("0831") || nomor.equals("0838") || nomor.equals("0832") || nomor.equals("0833") || nomor.equals("0896") || nomor.equals("0897") || nomor.equals("0899") || nomor.equals("0898") || nomor.equals("0816") || nomor.equals("0815") || nomor.equals("0858") || nomor.equals("0855") || nomor.equals("0814") || nomor.equals("0856") || nomor.equals("0857") || nomor.equals("0817") || nomor.equals("0818") || nomor.equals("0819") || nomor.equals("0877") || nomor.equals("0878") || nomor.equals("0859") || nomor.equals("0812") || nomor.equals("0895") || nomor.equals("0813") || nomor.equals("0821") || nomor.equals("0822") || nomor.equals("0823") || nomor.equals("0851") || nomor.equals("0852") || nomor.equals("0853") || nomor.equals("0881") || nomor.equals("0882") || nomor.equals("0883") || nomor.equals("0884") || nomor.equals("0885") || nomor.equals("0886") || nomor.equals("0887") || nomor.equals("0888") || nomor.equals("0889");
        }
        else
            return false;
    }


    public static  boolean validator(Integer min,Integer max,String jenis,String value) {
        if(jenis.equals("nomor")) {
            Pattern mPattern = Pattern.compile("^[0-9]{"+min+","+max+"}$");
            Matcher matcher = mPattern.matcher(value);

         //   if(value.length()>=min && value.length()<=max && !isNaN(value))
            return matcher.find();
        }

        if(jenis.equals("nomorhp")) {
            if(value.length()>4) {
                Pattern mPattern = Pattern.compile("^[0-9]{" + min + "," + max + "}$");
                Matcher matcher = mPattern.matcher(value);
                String nomor = value.substring(0, 4);
                return matcher.find() && (nomor.equals("0831") || nomor.equals("0838") || nomor.equals("0832") || nomor.equals("0833") || nomor.equals("0896") || nomor.equals("0897") || nomor.equals("0899") || nomor.equals("0898") || nomor.equals("0816") || nomor.equals("0815") || nomor.equals("0858") || nomor.equals("0855") || nomor.equals("0814") || nomor.equals("0856") || nomor.equals("0857") || nomor.equals("0817") || nomor.equals("0818") || nomor.equals("0819") || nomor.equals("0877") || nomor.equals("0878") || nomor.equals("0859") || nomor.equals("0812") || nomor.equals("0895") || nomor.equals("0813") || nomor.equals("0821") || nomor.equals("0822") || nomor.equals("0823") || nomor.equals("0851") || nomor.equals("0852") || nomor.equals("0853") || nomor.equals("0881") || nomor.equals("0882") || nomor.equals("0883") || nomor.equals("0884") || nomor.equals("0885") || nomor.equals("0886") || nomor.equals("0887") || nomor.equals("0888") || nomor.equals("0889"));
            }
            else
                return false;
        }
        if(jenis.equals("voucher")) {
            Pattern mPattern = Pattern.compile("^(?=.*[A-Z])[A-Z-\\d]{"+min+","+max+"}$");
            Matcher matcher = mPattern.matcher(value);
            return matcher.find();
        }

        if(jenis.equals("alfa")) {

            return value.length() >= min && value.length() <= max;
        }
//        if(jenis.equals("password")) {
//            if (value.search(/[A-Z]/) < 0)
//            return false;
//            if (value.search(/[a-z]/) < 0)
//            return false;
//            if (value.search(/[0-9]/) < 0)
//            return false;
//            if(value.length>=min && value.length<=max)
//                return true;
//
//        }
//
//        if(jenis=='email') {
//  const emailRe = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
//
//            if (emailRe.test(value)) {
//                return true;
//            } else {
//                return false;
//            }

            // if (value.match(/^\w+([.-]?\w+)_@\w+(_[_.-]?\w+)_(.\w{2,3})+$/))
            // return true;

            // else
            //   return false;

        return false;
        }



    public static String getMetodeDetail(Integer metode) {
        if(metode.equals(2))
            return "Stellar Lumens";
        if(metode.equals(3))
            return "IDK On-Chain";
        if(metode.equals(13))
            return "Indodax IDK";
        if(metode.equals(4))
            return "BCA";
        if(metode.equals(5))
            return "Mandiri";
        if(metode.equals(6))
            return "Dogecoin";
        if(metode.equals(7))
            return "BNI";
        if(metode.equals(8))
            return "Saldo";
        if(metode.equals(9))
            return "OVO";
        if(metode.equals(10))
            return "QRIS";
        if(metode.equals(11))
            return "Shopeepay";
        if(metode.equals(12))
            return "Linkaja";
        if(metode.equals(14))
            return "IDK On-Chain";
        if(metode.equals(15))
            return "Binance IDR (Giftcard)";
        if(metode.equals(16))
            return "Binance USD";
        if(metode.equals(17))
            return "Tokocrypto BIDR";
        if(metode.equals(18))
            return "Binance IDR (Transfer)";
        return "Error";
    }
    public static Integer getMetodeId(String metode) {
        if(metode.equals("Stellar Lumens"))
            return 2;
        if(metode.equals("Indodax"))
            return 3;
        if(metode.equals("BCA"))
            return 4;
        if(metode.equals("Mandiri"))
            return 5;
        if(metode.equals("Dogecoin"))
            return 6;
        if(metode.equals("BNI"))
            return 7;
        if(metode.equals("Saldo"))
            return 8;
        if(metode.equals("OVO"))
            return 9;
        if(metode.equals("QRIS"))
            return 10;
        if(metode.equals("Shopeepay"))
            return 11;
        if(metode.equals("Linkaja"))
            return 12;
        if(metode.equals("Indodax IDK"))
            return 13;
        if(metode.equals("IDK On-Chain"))
            return 14;
        if(metode.equals("Binance IDR (Giftcard)"))
            return 15;
        if(metode.equals("Binance USD"))
            return 16;
        if(metode.equals("Tokocrypto BIDR"))
            return 17;
        if(metode.equals("Binance BIDR (Transfer)"))
            return 18;
        return 0;
    }
    public static String statusdeposit(Integer status) {
        if(status.equals(0))
            return "Belum dibayar";
        if(status.equals(1))
            return "Deposit sukses";
        if(status.equals(2))
            return "Deposit dibatalkan";
        return "";
    }
}
