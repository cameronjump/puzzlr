package room323.puzzlr;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import static room323.puzzlr.PieceMatchActivity.pieces;

public class MyUtils {

    private static final String TAG = "UTILS";

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static double[][] f = {{0.4843121186003962, 0.3812298611062089, 0.42140849842867695, 0.4493233074986475, 0.47084436874655466, 0.3729447356203423, 0.43041865796112416, 0.613834445377833, 0.567421855797659, 0.5080748099940895, 0.6796477566692187, 0.64253796428152, 0.716159438751772, 0.7547561641620264, 0.836201845542506, 0.5225866858849177, 0.48747629895218747, 0.4780473623784767, 0.5232284351393656}, {0.44808073833109113, 0.41253709934613597, 0.5396212780949802, 0.4159305267927105, 0.4971111657311032, 0.6445498481942139, 0.5613098358982982, 0.5369374827128794, 0.525810192390009, 0.6979587881463778, 0.634104095579567, 0.4888311029337995, 0.4914079401068256, 0.6594647426168353, 0.5740267391244359, 0.4343962198401916, 0.5080664672537817, 0.49416617840244226, 0.4308338697287519}, {0.6583712018872562, 0.7547458961739552, 0.6137214975090504, 0.5760386230371297, 0.7324868232834331, 0.6205728125495351, 0.46332948497697085, 0.43133571764573003, 0.6208076927766629, 0.45600391723744915, 0.421733865300682, 0.5209155708263355, 0.6066526294713077, 0.4473527094546565, 0.5448637277545643, 0.6854427524368824, 0.7745887831214813, 0.704529658762669, 0.6501831231497567}, {0.5298602719348291, 0.5305020211892769, 0.565374033926716, 0.6077442449531235, 0.4091748323911385, 0.5957339076561328, 0.5303351663831205, 0.42910435548801507, 0.3957077242865513, 0.49296739079513374, 0.682065867859978, 0.8032101581205988, 0.6362160923759547, 0.5575517522642519, 0.8367030517102297, 0.46483117823237874, 0.6348112319524958, 0.5331209998966784, 0.6039194193966145}, {0.7990124762472557, 0.3933903677287403, 0.4172230097911684, 0.3900988358026775, 0.8327274150789256, 0.653905268825554, 0.851533235231264, 0.6910798778879519, 0.481444141182269, 0.6675957056706889, 0.532948369347232, 0.5332807954610359, 0.6271725618822762, 0.7102970593123913, 0.38952575371845566, 0.7456277801558153, 0.4322540608288448, 0.4400128093151188, 0.38828967334891645}, {0.4785504937939638, 0.8486081421294909, 0.6493154781577433, 0.5135444388897481, 0.7033109769284726, 0.612851285520019, 0.4889737851847051, 0.5432798905945871, 0.7926161614281746, 0.47148355100398465, 0.5404241777177672, 0.43390463991128464, 0.49683229002730933, 0.4100636551085487, 0.5743498955267866, 0.6351815925777848, 0.8408429761506725, 0.6274838102706835, 0.4843288040810119}, {0.616042205424079, 0.4799283294432633, 0.6354248155452206, 0.8370906682599162, 0.44842164979614835, 0.3076125223533744, 0.34549854611706404, 0.4971196510823565, 0.4512203182947952, 0.5781916917858021, 0.7028925564145726, 0.6822115449407377, 0.5159432976028739, 0.48938193771053384, 0.6416863630208678, 0.5698291285565655, 0.5421469179385959, 0.6540381109212247, 0.7767899830642372}, {0.4392260247291658, 0.47779451317222427, 0.4651372926267503, 0.44731912457700707, 0.48326093332161074, 0.7363873752519668, 0.5824721593129689, 0.6328911894886606, 0.5048442442471992, 0.576570633169067, 0.6172379982015334, 0.3409121781119438, 0.658792688536483, 0.828493153498079, 0.625915375092813, 0.4569684663668842, 0.4445660915808987, 0.4911884618618045, 0.41688031568929323}, {0.5819356569362506, 0.6522970451939077, 0.8697345275859126, 0.6198482776412634, 0.6068601997023853, 0.5902467376141859, 0.5651707420240016, 0.6937662402670705, 0.8433053680399887, 0.6813901058950446, 0.40150913754675943, 0.6488862192119904, 0.5210644566533675, 0.6798064113460127, 0.5137286209257746, 0.6947121786681265, 0.7752286071281657, 0.8108514664933087, 0.6506952390548061}, {0.4864157726564762, 0.4771740842541187, 0.6130611375262235, 0.8215609780515338, 0.8437436827807765, 0.4705485223402542, 0.9136539213125568, 0.8047285368566223, 0.9251065785074325, 0.5637414238234009, 0.7901678880224561, 0.7971745063825173, 0.801204049951195, 0.5781358596006652, 0.6319157306219, 0.44531750865238434, 0.5772156624752597, 0.6162133385585984, 0.880861817908783}, {0.45560346570267374, 0.5989445791761352, 0.778545167275152, 0.5958962702175081, 0.5565981128721424, 0.8502888834268897, 0.871709831791103, 0.7434042616000991, 0.5911993074242046, 0.6673723769301412, 0.522435233060868, 0.48556531228338734, 0.6494900339549531, 0.916751003214522, 0.7933939615245652, 0.5609620078023875, 0.6541382238049185, 0.5473363876319517, 0.5233144295394615}, {1.0, 0.8773251377675213, 0.7242961775489157, 0.5079843233492124, 0.5267085700407632, 0.42154868498803744, 0.5287115407693675, 0.6316192424663452, 0.8339403211698319, 0.6860203267658854, 0.40629466173717677, 0.6155259538016121, 0.7457447211310703, 0.5481066293482345, 0.49210830245984627, 0.8174107856230198, 0.8226455342915506, 0.6543268980857262, 0.43801247688900496}, {0.32882376132232866, 0.5469678809489532, 0.5881227614983818, 0.707063284819229, 0.643884995966606, 0.5967151422661835, 0.5782455987231757, 0.7770235797928562, 0.6642839229903748, 0.8008360424065055, 0.9047252639354246, 0.8804729178605878, 0.5279452921595569, 0.46321175964151606, 0.352021927288526, 0.41245802157689343, 0.44468374561088075, 0.5169495604338482, 0.5708937192642217}, {0.5395205234620318, 0.6137062381378889, 0.4131113223179213, 0.4703259066544335, 0.7843132095741288, 0.9755859331130422, 0.6370914383590215, 0.4380317293666384, 0.4799957131149803, 0.4327109862980117, 0.5212781591550986, 0.4049745835207776, 0.5201146677567847, 0.42521449934038874, 0.6410330622798399, 0.48291759747048113, 0.5951019272514471, 0.5191013456840117, 0.506266004067692}, {0.6955766149138677, 0.5044758801751462, 0.4500683783830614, 0.5562791634926818, 0.49818417048454, 0.5874797287454252, 0.45687220397871703, 0.35169784391502984, 0.4317788811586626, 0.4754042824212972, 0.6068085745401385, 0.4035454079311223, 0.4829312881212427, 0.5140405110634363, 0.7294808697755996, 0.49872773210305726, 0.47271635132177886, 0.44358414391612083, 0.7180320630762508}};


    public static double[][] createDummyArray() {
        double[][] d = new double[15][20];
        for(int i=0; i<d.length; i++) {
            for(int j=0; j<d[0].length; j++) {
                d[i][j] = (float) .5;
            }
        }
        Log.d(TAG, d.toString());
        return d;
    }

    public static int getColor(Double d) {
        if (d==1) return R.color.grad2;
        else if (d>=.93) return R.color.grad5;
        else if (d>=.87) return R.color.grad4;
        else if (d>=.8) return R.color.grad3;
        else return R.color.grad2;
    }

    public static double[][] stringToArray(String s) {
        String[] s1 = s.split("],[");
        double[][] d = new double[s1.length][pieces/s1.length];
        for (int i=0; i<s1.length; i++) {
            String s2 = s1[i].replace("[","").replace("]", "");
            String[] b = s2.split(",");
            for (int j=0; j<b.length; j++) {
                d[i][j] = Double.valueOf(b[j]);
            }
        }
        return d;
    }

    public static double[][] camsMethod(String s) {
        String[] a = s.replace("[","").replace("]","").split(",");
        int row = Integer.valueOf(a[0]);
        int height = Integer.valueOf(a[1]);
        double[][] d = new double[row][height];
        int r=0;
        int h=0;
        for (int i=2; i<a.length; i++) {
            Log.d(TAG, "row" + String.valueOf(r) + " height" + String.valueOf(h));
            d[h][r]=Double.parseDouble(a[i]);
            if(h==row-1) {
                h = 0;
                r = r + 1;
            }
            else {
                h = h + 1;
            }
        }
        return d;
    }

    public static double[][] visaalsMethod(String arrString) {
        // get rid of first bracket and last bracked
        arrString = arrString.substring(1, arrString.length()-1);

        String[] arrs = arrString.split("]");
        int len = arrString.length();
        int i = 0;
        ArrayList<String> al = new ArrayList<>();
        while (i < len) {
            char curr = arrString.charAt(i);
            if (curr == '[') {
                String row = "";
                i++;
                while (arrString.charAt(i) != ']') {
                    row += arrString.charAt(i);
                    i++;
                }

                String rowArr = row;
                al.add(rowArr);
            }
            i++;
        }

        double[][] ans = new double[al.size()][al.get(0).split(",").length];
        int idx = 0;
        for (String s: al) {
            Log.d(TAG,s);

            String[] temp = s.split(",");

            double[] dtemp = new double[temp.length];
            int didx = 0;
            for (String ss: temp) {
                dtemp[didx] = Double.parseDouble(ss);
                didx++;
            }

            ans[idx] = dtemp;
            idx++;
        }

        Log.d(TAG, "Length: " + ans.length);
        Log.d(TAG, "width: " + ans[1].length);

        for (i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                //Log.d(TAG,String.valueOf(ans[i][j]));
            }

        }

        return ans;
    }

    public static double[][] visaalsSecondMethod(ArrayList<Double> arraylistname) {
        int length = arraylistname.get(0).intValue();
        int width = arraylistname.get(1).intValue();

        double[][] ans = new double[length][width];

        int idx = 2;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                ans[i][j] = arraylistname.get(idx);
                idx++;
            }
        }
        return ans;
    }



    public static double[][] johnsMethod(String str) {
        str = str.replaceAll("[^-?0-9.]+", " ");
        ArrayList<Double> arrli = new ArrayList<Double>();
        String[] integerStrings = str.split(" ");
        for (String num : integerStrings){
            System.out.println(num);
            if (num.trim().length() > 0){
                double nums = Double.parseDouble(num);
                arrli.add(nums);
            }
        }
        return visaalsSecondMethod(arrli);
    }

//    public static void logArray(double[][] array) {
//        for(int i=0; array.length;i++) {
//            for(int j=0; array[i])
//        }
//    }

}
