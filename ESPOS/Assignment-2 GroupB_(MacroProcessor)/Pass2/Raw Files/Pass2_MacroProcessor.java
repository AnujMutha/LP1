package com.muthadevs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class Main {

    static String[][] mnt = new String[5][3];
    @SuppressWarnings("unchecked")
    static HashMap<String, String>[] ala = new HashMap[10];
    static HashMap<String, Integer> mntmp = new HashMap<>();
    static String[][] mdt = new String[20][2];


    static int mntc = 0, mdtc = 0, alac = 0;

    public static void main(String[] args) throws Exception {
        alac = 0;
        String s;
        BufferedReader alaf = new BufferedReader(new FileReader("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_Macroprocessor_A\\Files\\ALA.txt"));
        BufferedReader mntf = new BufferedReader(new FileReader("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_Macroprocessor_A\\Files\\MNT.txt"));
        BufferedReader mdtf = new BufferedReader(new FileReader("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_Macroprocessor_A\\Files\\MDT.txt"));
        while ((s = mntf.readLine()) != null) {
            String[] arr = s.split("\t\t");
            if (arr.length > 2) {
                mnt[mntc][0] = arr[0];
                mnt[mntc][1] = arr[1];
                mnt[mntc][2] = arr[2];
                mntmp.put(arr[1], 1);
                mntc++;
            }
        }
        while ((s = mdtf.readLine()) != null) {
            String[] arr = s.split("\t\t");
            if (arr.length > 1) {
                mdt[mdtc][0] = arr[0];
                mdt[mdtc][1] = arr[1];
                mdtc++;
            }
        }
        while ((s = alaf.readLine()) != null) {
            String[] arr1 = s.split("\\s+");
            HashMap<String, String> mp = new HashMap<>();
            for (int i = 0; i < arr1.length; i++) {
                s = alaf.readLine();
                String[] arr2 = s.split("\t\t\t\t\t");
                if (arr2.length == 3) {
                    mp.put(arr2[1], arr2[2]);
                }
            }
            ala[alac++] = mp;
        }
        pass2();
    }

    static void pass2() {
        String s;
        alac = 0;
        try {
            boolean flag;
            int i;
            BufferedReader inp = new BufferedReader(new FileReader("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass1_Macroprocessor_A\\Files\\Pass1_MP_Output.txt"));
            BufferedWriter op = new BufferedWriter(new FileWriter("D:\\IntellijWorkspace\\SEM5(EPOS)\\Pass2_Macroprocessor_A\\Files\\Pass2_MP_Output.txt"));

            while ((s = inp.readLine()) != null) {
                String[] arr = s.split("\\s+");
                flag=false;
                for (i = 0; i < mntc; i++) {
                    if (mnt[i][1].equals(arr[0])) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    int ind = Integer.parseInt(mnt[i][2]);
                    while (!mdt[ind][1].equals("MEND")) {
                        String[] a2 = mdt[ind][1].split("\\s+");
                        op.write(a2[0] + " ");
                        for (int j = 1; j < a2.length; j++) {
                            op.write(ala[alac].get(a2[j]) + " ");
                        }
                        op.write("\n");
                        ind++;
                    }
                    alac++;
                } else {
                    op.write(s + "\n");
                }
            }
            op.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
