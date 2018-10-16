package tensorMing_Fundation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Variable {

    private String name;
    private int dimension;
    private float[] v1;
    private float[][] v2;
    private float[][][] v3;
    private float[][][][] v4;

    /*
    Initialization should be chose from: arange, zeros, random, xavier.
    You'd better to have a specific clue on the name of the variables.
    Dimens should look like [2,3,10,10] or lower dimensions.
     */
    public Variable(String str, int[] dimens, String initialization) {
        name = str;
        dimension = dimens.length;
        switch (dimension) {
            case 1: if (initialization.equals("zeros")) {
                        v1 = Initializer.zeros1d(dimens);
                    } else if (initialization.equals("random")) {
                        v1 = Initializer.random1d(dimens);
                    } else if (initialization.equals("xavier")) {
                        v1 = Initializer.xavier1d(dimens);
                    } else if (initialization.equals("arange")) {
                        v1 = Initializer.arange(dimens[0]);
                    } else {
                        throw new IllegalArgumentException("Invalid initialization method.");
                    }
                    break;
            case 2: if (initialization.equals("zeros")) {
                        v2 = Initializer.zeros2d(dimens);
                    } else if (initialization.equals("random")) {
                        v2 = Initializer.random2d(dimens);
                    } else if (initialization.equals("xavier")) {
                        v2 = Initializer.xavier2d(dimens);
                    } else {
                        throw new IllegalArgumentException("Invalid initialization method.");
                    }
                    break;
            case 3: if (initialization.equals("zeros")) {
                        v3 = Initializer.zeros3d(dimens);
                    } else if (initialization.equals("random")) {
                        v3 = Initializer.random3d(dimens);
                    } else if (initialization.equals("xavier")) {
                        v3 = Initializer.xavier3d(dimens);
                    } else {
                        throw new IllegalArgumentException("Invalid initialization method.");
                    }
                    break;
            case 4: if (initialization.equals("zeros")) {
                        v4 = Initializer.zeros4d(dimens);
                    } else if (initialization.equals("random")) {
                        v4 = Initializer.random4d(dimens);
                    } else if (initialization.equals("xavier")) {
                        v4 = Initializer.xavier4d(dimens);
                    } else {
                        throw new IllegalArgumentException("Invalid initialization method.");
                    }
                    break;
            default: throw new NumberFormatException("The dimension is not supported.");
        }
    }

    public Variable(String str, float[] values) {
        name = str;
        dimension = 1;
        v1 = values;
    }

    public Variable(String str, float[][] values) {
        name = str;
        dimension = 2;
        v2 = values;
    }

    public Variable(String str, float[][][] values) {
        name = str;
        dimension = 3;
        v3 = values;
    }

    public Variable(String str, float[][][][] values) {
        name = str;
        dimension = 4;
        v4 = values;
    }

    public Variable(float[] values) {
        name = "temp";
        dimension = 1;
        v1 = values;
    }

    public Variable(float[][] values) {
        name = "temp";
        dimension = 2;
        v2 = values;
    }

    public Variable(float[][][] values) {
        name = "temp";
        dimension = 3;
        v3 = values;
    }

    public Variable(float[][][][] values) {
        name = "temp";
        dimension = 4;
        v4 = values;
    }

    public Variable(String str, String filePath) {
        name = str;
        load(filePath);
    }

    public boolean equals(Variable v) {
        int[] vShape = v.getShape();
        int[] mShape = getShape();
        if (!(Arrays.equals(vShape, mShape))) {
            return false;
        }
        if (mShape.length == 1) {
            return Arrays.equals(get1d(), v.get1d());
        } else if (mShape.length == 2) {
            float[][] mValue = get2d();
            float[][] vValue = v.get2d();
            for (int i = 0 ; i<mShape[0] ; i++) {
                if (!Arrays.equals(mValue[i], vValue[i])) return false;
            }
            return true;
        } else if (mShape.length == 3) {
            float[][][] mValue = get3d();
            float[][][] vValue = v.get3d();
            for (int i = 0 ; i<mShape[0] ; i++) {
                for (int j = 0 ; j<mShape[1] ; j++) {
                    if (!Arrays.equals(mValue[i][j], vValue[i][j])) return false;
                }
            }
            return true;
        } else if (mShape.length == 4) {
            float[][][][] mValue = get4d();
            float[][][][] vValue = v.get4d();
            for (int i = 0 ; i<mShape[0] ; i++) {
                for (int j = 0 ; j<mShape[1] ; j++) {
                    for (int k = 0 ; k<mShape[2] ; k++) {
                        if (!Arrays.equals(mValue[i][j][k], vValue[i][j][k])) return false;
                    }
                }
            }
            return true;
        } else {
            throw new IllegalArgumentException("I have no idea what is going on.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public int[] getShape(){
        int[] result = new int[dimension];
        for (int i = 0 ; i<dimension ; i++) {
            switch (i) {
                case 0: switch (dimension) {
                    case 1: result[i] = v1.length;
                        break;
                    case 2: result[i] = v2.length;
                        break;
                    case 3: result[i] = v3.length;
                        break;
                    case 4: result[i] = v4.length;
                        break;
                } break;
                case 1: switch (dimension) {
                    case 2: result[i] = v2[0].length;
                        break;
                    case 3: result[i] = v3[0].length;
                        break;
                    case 4: result[i] = v4[0].length;
                        break;
                } break;
                case 2: switch (dimension) {
                    case 3: result[i] = v3[0][0].length;
                        break;
                    case 4: result[i] = v4[0][0].length;
                        break;
                } break;
                case 3: result[i] = v4[0][0][0].length;
                    break;
            }
        }
        return result;
    }

    public int getDimension() {
        return dimension;
    }

    public void setValue(float[] values) {
        dimension = 1;
        v1 = values;
        v2 = null;
        v3 = null;
        v4 = null;
    }

    public void setValue(float[][] values) {
        dimension = 2;
        v1 = null;
        v2 = values;
        v3 = null;
        v4 = null;
    }

    public void setValue(float[][][] values) {
        dimension = 3;
        v1 = null;
        v2 = null;
        v3 = values;
        v4 = null;
    }

    public void setValue(float[][][][] values) {
        dimension = 4;
        v1 = null;
        v2 = null;
        v3 = null;
        v4 = values;
    }

    public void importValues(float[] values) {
        if (dimension != 1) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values.length != v1.length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        v1 = values;
    }

    public void importValues(float[][] values) {
        if (dimension != 2) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values.length != v2.length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0].length != v2[0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        v2 = values;
    }

    public void importValues(float[][][] values) {
        if (dimension != 3) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values.length != v3.length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0].length != v3[0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0][0].length != v3[0][0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        v3 = values;
    }

    public void importValues(float[][][][] values) {
        if (dimension != 4) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values.length != v4.length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0].length != v4[0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0][0].length != v4[0][0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        if (values[0][0][0].length != v4[0][0][0].length) {
            throw new NumberFormatException("The dimension of imported values does not match "+name+".");
        }
        v4 = values;
    }

    public float[] get1d() {
        if (dimension != 1) {
            throw new NumberFormatException("The dimension of "+name+" is not an 1.");
        }
        return v1;
    }

    public float[][] get2d() {
        if (dimension != 2) {
            throw new NumberFormatException("The dimension of "+name+" is not an 2.");
        }
        return v2;
    }

    public float[][][] get3d() {
        if (dimension != 3) {
            throw new NumberFormatException("The dimension of "+name+" is not an 3.");
        }
        return v3;
    }

    public float[][][][] get4d() {
        if (dimension != 4) {
            throw new NumberFormatException("The dimension of "+name+" is not an 4.");
        }
        return v4;
    }

    public String serialize() {
        int[] shape = getShape();
        StringBuilder sb = new StringBuilder();
        sb.append(dimension+"\n");
        sb.append(Arrays.toString(shape)+"\n");
        if (dimension == 1) {
            sb.append(Arrays.toString(v1));
        } else if (dimension == 2) {
            for (int i = 0 ; i<shape[0] ; i++) {
                sb.append(Arrays.toString(v2[i]));
                sb.append("\n");
            }
        } else if (dimension == 3) {
            for (int i = 0 ; i<shape[0] ; i++) {
                for (int j = 0 ; j<shape[1] ; j++) {
                    sb.append(Arrays.toString(v3[i][j]));
                    sb.append("\n");
                }
            }
        } else if (dimension == 4) {
            for (int l = 0 ; l<shape[0] ; l++) {
                for (int i = 0 ; i<shape[1] ; i++) {
                    for (int j = 0 ; j<shape[2] ; j++) {
                        sb.append(Arrays.toString(v4[l][i][j]));
                        sb.append("\n");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal shape.");
        }
        return sb.toString();
    }

    public void deserializeAndLoad(String str) {
        String[] lines = str.split("\n");
        if (lines[0].equals("1")) {
            String[] data = lines[1].substring(1,lines[1].length()-1).split(", ");
            int[] shape = new int[data.length];
            for (int i = 0 ; i<data.length ; i++) {
                shape[i] = Integer.parseInt(data[i]);
            }
            String[] values = lines[2].substring(1,lines[2].length()-1).split(", ");
            float[] result = new float[shape[0]];
            for (int i = 0 ; i<values.length ; i++) {
                result[i] = Float.parseFloat(values[i]);
            }
            setValue(result);
            return;
        } else if (lines[0].equals("2")) {
            String[] data = lines[1].substring(1,lines[1].length()-1).split(", ");
            int[] shape = new int[data.length];
            for (int i = 0 ; i<data.length ; i++) {
                shape[i] = Integer.parseInt(data[i]);
            }
            float[][] result = new float[shape[0]][shape[1]];
            for (int i = 2 ; i<lines.length ; i++) {
                String[] lineStrArray = lines[i].substring(1, lines[i].length()-1).split(", ");
                for (int j = 0 ; j<lineStrArray.length ; j++) {
                    result[i-2][j] = Float.parseFloat(lineStrArray[j]);
                }
            }
            setValue(result);
            return;
        } else if (lines[0].equals("3")) {
            String[] data = lines[1].substring(1,lines[1].length()-1).split(", ");
            int[] shape = new int[data.length];
            for (int i = 0 ; i<data.length ; i++) {
                shape[i] = Integer.parseInt(data[i]);
            }
            float[][][] result = new float[shape[0]][shape[1]][shape[2]];
            for (int i = 2 ; i<lines.length ; i++) {
                int dim1 = (i-2)/shape[1];
                int dim2 = i-2-dim1*shape[1];
                String[] lineStrArray = lines[i].substring(1, lines[i].length()-1).split(", ");
                for (int j = 0 ; j<lineStrArray.length ; j++) {
                    result[dim1][dim2][j] = Float.parseFloat(lineStrArray[j]);
                }
            }
            setValue(result);
            return;
        } else if (lines[0].equals("4")) {
            String[] data = lines[1].substring(1,lines[1].length()-1).split(", ");
            int[] shape = new int[data.length];
            for (int i = 0 ; i<data.length ; i++) {
                shape[i] = Integer.parseInt(data[i]);
            }
            float[][][][] result = new float[shape[0]][shape[1]][shape[2]][shape[3]];
            for (int i = 2 ; i<lines.length ; i++) {
                int dim1 = (i-2)/(shape[1]*shape[2]);
                int dim2 = (i-2-dim1*shape[1]*shape[2])/shape[2];
                int dim3 = i-2-dim1*shape[1]*shape[2]-dim2*shape[2];
                String[] lineStrArray = lines[i].substring(1, lines[i].length()-1).split(", ");
                for (int j = 0 ; j<lineStrArray.length ; j++) {
                    result[dim1][dim2][dim3][j] = Float.parseFloat(lineStrArray[j]);
                }
            }
            setValue(result);
            return;
        } else {
            throw new IllegalArgumentException("Invalid data to be deserialized.");
        }
    }

    public boolean save(String filePath) {
        String lines = serialize();
        try {
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
            w.write(lines);
            w.flush();
            w.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean load(String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String everything = sb.toString();
            deserializeAndLoad(everything);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        if (dimension == 1) {
            if (v1.length<=100) {
                return Arrays.toString(v1)+"\n";
            } else {
                return "["+v1[0]+", "+v1[1]+", "+v1[2]+", ... , "+v1[v1.length-1]+"]\n";
            }
        }
        if (dimension == 2) {
            if (v2.length<=20 && v2[0].length<=100) {
                StringBuilder sb = new StringBuilder();
                sb.append("[ ");
                for (int i = 0 ; i<v2.length ; i++) {
                    if (i!=0) sb.append("  ");
                    sb.append(Arrays.toString(v2[i]));
                    if (i==(v2.length-1)) sb.append(" ]");
                    sb.append("\n");
                }
                return sb.toString();
            } else if (v2.length>20 && v2[0].length<=100){
                StringBuilder sb = new StringBuilder();
                sb.append("[ ");
                for (int i = 0 ; i<3 ; i++) {
                    if (i!=0) sb.append("  ");
                    sb.append(Arrays.toString(v2[i]));
                    sb.append("\n");
                }
                sb.append("   ...\n  ");
                sb.append("["+v2[v2.length-1][0]+", "+v2[v2.length-1][1]+", "+v2[v2.length-1][2]+", ... , "+v2[v2.length-1][v2.length-1]+"] ]");
                sb.append("\n");
                return sb.toString();
            } else if (v2.length<=20 && v2[0].length>100){
                StringBuilder sb = new StringBuilder();
                sb.append("[ ");
                for (int i = 0 ; i<v2.length ; i++) {
                    if (i!=0) sb.append("  ");
                    sb.append("["+v2[i][0]+", "+v2[i][1]+", "+v2[i][2]+", ... , "+v2[i][v2.length-1]+"]");
                    if (i==(v2.length-1)) sb.append(" ]");
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("[ ");
                for (int i = 0 ; i<3 ; i++) {
                    if (i!=0) sb.append("  ");
                    sb.append("["+v2[i][0]+", "+v2[i][1]+", "+v2[i][2]+", ... , "+v2[i][v2.length-1]+"]");
                    sb.append("\n");
                }
                sb.append("   ...\n  ");
                sb.append("["+v2[v2.length-1][0]+", "+v2[v2.length-1][1]+", "+v2[v2.length-1][2]+", ... , "+v2[v2.length-1][v2.length-1]+"] ]");
                sb.append("\n");
                return sb.toString();
            }
        }
        return "I am too dumb to print out 3d or higher dimension arrays.";
    }
}