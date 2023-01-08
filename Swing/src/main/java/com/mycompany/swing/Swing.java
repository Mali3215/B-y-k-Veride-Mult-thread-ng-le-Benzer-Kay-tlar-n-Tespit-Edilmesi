/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.swing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author akdme
 */
public class Swing extends javax.swing.JFrame {

    static int sayac = 0;
    List nations = null;
    DefaultListModel model;
    DefaultListModel modelth;
    List<Thread> th;

    /**
     * Creates new form Swing
     */
    public Swing() throws IOException {
        nations = readCSV();
        initComponents();
    }

    public static List readCSV() throws FileNotFoundException, IOException {

        List row = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("C:\\rows.csv\\rows.csv"));
        String line; // Reading header, Ignoring

        while ((line = br.readLine()) != null) {
            try {
                String[] fields = line.split(",");

                int _id = sayac;
                String product = fields[1];
                String issue = fields[3];
                String company = fields[7];
                String state = fields[8];
                String zipCode = fields[9];
                String complaintID = fields[fields.length - 1];
                if (product.length() == 0) {
                    continue;
                } else if (issue.length() == 0) {
                    continue;
                } else if (company.length() == 0) {
                    continue;
                } else if (state.length() == 0) {
                    continue;
                } else if (zipCode.length() == 0) {
                    continue;
                } else if (complaintID.length() == 0) {
                    continue;
                }
                Row nation = new Row(_id, product, issue, company, state, zipCode, complaintID);
                sayac++;
                row.add(nation);

            } catch (Exception e) {
                System.out.println("hata: " + e);

            }

        }

        br.close();

        return row;
    }

    public class Tab1 extends Thread implements Runnable {

        List row;
        Object[] temp;
        public long totalTime;

        static int say = 0;
        static int caunt = 0;
        static int cmp = 0;
        String[] data;
        static int size;

        public Tab1(List list, int incomingData, int cmp) throws FileNotFoundException, IOException {

            this.cmp = cmp;
            this.temp = list.toArray();
            say = incomingData;
            data = Arrays.toString(temp).toLowerCase().split(",");
          

        }

        public void run() {
            long start = System.currentTimeMillis();
            toCompare();
            long end = System.currentTimeMillis();

            this.totalTime = end - start;

        }

        public void toCompare() {
            modelth = new DefaultListModel();

            for (size = say; size < data.length; size = size + 7) {

                String[] tp = data[size].toLowerCase().split(" ");

                float max = tp.length;

                for (caunt = size + 7; caunt < data.length; caunt = caunt + 7) {
                    String[] tp2 = data[caunt].toLowerCase().split(" ");
                    float min = tp2.length;

                    try {
                        if (min <= max) {

                            int wordCaunter = 0;
                            for (int i = 0; i < min; i++) {
                                try {
                                    if (!tp2[i].equals("")) {

                                        for (int j = 0; j < max; j++) {

                                            if (tp2[i].equals(tp[j])) {

                                                wordCaunter++;
                                                break;
                                            }
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            if ((((wordCaunter / max) * 100) >= cmp) && caunt < data.length && size < data.length) {
                                modelth.addElement(Thread.currentThread().getName() + "          " + "      " + data[size] + "            " + data[caunt]
                                        + "        " + "benzerlik orani:" + ((wordCaunter / max) * 100));
                                lstThread1.setModel(modelth);
                              
                            }
                            wordCaunter = 0;

                        } else {
                            int wordCaunter = 0;
                            for (int i = 0; i < max; i++) {
                                try {

                                    if (!tp[i].equals("")) {

                                        for (int j = 0; j < min; j++) {

                                            if (tp[i].equals(tp2[j])) {

                                                wordCaunter++;
                                                break;
                                            }

                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            if ((((wordCaunter / min) * 100) >= cmp) && caunt < data.length && size < data.length) {

                                modelth.addElement(Thread.currentThread().getName() + "        " + size + "         " + data[size] + "          "
                                        + data[caunt] + "          " + "benzerlik orani:" + ((wordCaunter / min) * 100));
                                lstThread1.setModel(modelth);
                               
                            }
                            wordCaunter = 0;

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public class Tab2 extends Thread implements Runnable {

        Object[] temp;
        public long totalTime;
        static int sayy = 0;
        static int caunt = 0;
        int same = 6;
        int cmp = 0;
        String[] data;
        static int size;
        String complaint;

        public Tab2(List list, int incomingData, int cmp, String complaint) throws FileNotFoundException, IOException {
            this.complaint = complaint;
            this.cmp = cmp;
            this.temp = list.toArray();
            sayy = incomingData;
            this.data = Arrays.toString(temp).toLowerCase().split(",");

        }

        public void run() {
            long start = System.currentTimeMillis();
            toCompare();
            long end = System.currentTimeMillis();

            this.totalTime = end - start;

        }

        public void toCompare() {
            modelth = new DefaultListModel();

            for ( size = sayy; size < data.length; size = size + 7, same = same + 7) {
                if (data[same].equals(complaint)) {
                    String[] tp = data[size].toLowerCase().split(" ");

                    float max = tp.length;

                    for (caunt = size + 7; caunt < data.length; caunt = caunt + 7) {
                        String[] tp2 = data[caunt].toLowerCase().split(" ");
                        float min = tp2.length;
                        try {
                            if (min <= max) {

                                int wordCaunter = 0;
                                for (int i = 0; i < min; i++) {
                                    try {
                                        if (!tp2[i].equals("")) {

                                            for (int j = 0; j < max; j++) {

                                                if (tp2[i].equals(tp[j])) {

                                                    wordCaunter++;
                                                        break;
                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                if ((((wordCaunter / max) * 100) >= cmp) && caunt < data.length && size < data.length) {
                                    modelth.addElement(Thread.currentThread().getName() + "      " + data[size] + "            " + data[caunt]
                                            + "        " + "benzerlik orani:" + ((wordCaunter / max) * 100));
                                    lstThreadTab2.setModel(modelth);

                                }
                                wordCaunter = 0;

                            } else {
                                int wordCaunter = 0;
                                for (int i = 0; i < max; i++) {
                                    try {

                                        if (!tp[i].equals("")) {

                                            for (int j = 0; j < min; j++) {

                                                if (tp[i].equals(tp2[j])) {

                                                    wordCaunter++;
                                                        break;

                                                }

                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                if ((((wordCaunter / min) * 100) >= cmp) && caunt < data.length && size < data.length) {

                                    modelth.addElement(Thread.currentThread().getName() + "        " + data[size] + "          "
                                            + data[caunt] + "          " + "benzerlik orani:" + ((wordCaunter / min) * 100));
                                    lstThreadTab2.setModel(modelth);

                                }
                                wordCaunter = 0;

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }

    }

    public class Tab3 extends Thread implements Runnable {

        List row;
        Object[] temp;
        public long totalTime;
        int incomingData;
        static int sayyy = 0;
        static int caunt = 0;

        int cmp = 0;
        String[] data;
        static int size;

        int look;
        static int same;

        public Tab3(List list, int incomingData, int cmp, int same, int look) throws FileNotFoundException, IOException {

            this.same = same;
            this.look = look;
            this.cmp = cmp;
            model = new DefaultListModel();

            this.temp = list.toArray();
            sayyy = incomingData;
            data = Arrays.toString(temp).toLowerCase().split(",");

        }

        public void run() {
            long start = System.currentTimeMillis();
            toCompare();
            long end = System.currentTimeMillis();

            this.totalTime = end - start;
        }

        public void toCompare() {
            modelth = new DefaultListModel();

            for (same = same, size = sayyy; size < data.length; size = size + 7, same = same + 7) {
                if (data[same].equals(data[same + 7]) && same + 7 < data.length) {
                    String[] tp = data[size].toLowerCase().split(" ");

                    float max = tp.length;

                    for (caunt = size + 7; caunt < data.length; caunt = caunt + 7) {
                        String[] tp2 = data[caunt].toLowerCase().split(" ");
                        float min = tp2.length;
                        try {
                            if (min <= max) {

                                int wordCaunter = 0;
                                for (int i = 0; i < min; i++) {
                                    try {
                                        if (!tp2[i].equals("")) {

                                            for (int j = 0; j < max; j++) {

                                                if (tp2[i].equals(tp[j])) {

                                                    wordCaunter++;
                                                        break;

                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                if (((wordCaunter / max) * 100) >= cmp && caunt < data.length && size < data.length) {

                                    modelth.addElement(Thread.currentThread().getName() + "        " + data[size - sayyy + look] + "            " + data[caunt - sayyy + look]
                                            + "            " + "benzerlik orani:" + ((wordCaunter / min) * 100));

                                    modelth.addElement(Thread.currentThread().getName() + "              " + data[size - sayyy + look] + "            " + data[caunt - sayyy + look]);
                                    lstThreadTab3.setModel(modelth);

                                }
                                wordCaunter = 0;

                            } else {
                                int wordCaunter = 0;
                                for (int i = 0; i < max; i++) {
                                    try {

                                        if (!tp[i].equals("")) {

                                            for (int j = 0; j < min; j++) {

                                                if (tp[i].equals(tp2[j])) {

                                                    wordCaunter++;
                                                        break;

                                                }

                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                                if ((((wordCaunter / min) * 100) >= cmp) && caunt < data.length && size < data.length) {

                                    modelth.addElement(Thread.currentThread().getName() + "              " + data[size - sayyy + look] + "          " + data[caunt - sayyy + look]);
                                    lstThreadTab3.setModel(modelth);

                                }
                                wordCaunter = 0;

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pane1 = new javax.swing.JTabbedPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblThread = new javax.swing.JLabel();
        textThread = new javax.swing.JTextField();
        lblCompareTo = new javax.swing.JLabel();
        textCompare = new javax.swing.JTextField();
        buttonCompare = new javax.swing.JButton();
        lblCompare = new javax.swing.JLabel();
        comboCompare = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        labelThread = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstThreadTime = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstThread1 = new javax.swing.JList<>();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblThread1 = new javax.swing.JLabel();
        textThreadTab2 = new javax.swing.JTextField();
        lblCompareTo1 = new javax.swing.JLabel();
        textCompareTab2 = new javax.swing.JTextField();
        buttonCompareTab2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textComplaintTab2 = new javax.swing.JTextField();
        lblCompare1 = new javax.swing.JLabel();
        comboCompareTab2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstThreadTimeTab2 = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        labelThreadTab2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lstThreadTab2 = new javax.swing.JList<>();
        jDesktopPane3 = new javax.swing.JDesktopPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblThread2 = new javax.swing.JLabel();
        textThreadTab3 = new javax.swing.JTextField();
        lblCompareTo2 = new javax.swing.JLabel();
        textCompareTab3 = new javax.swing.JTextField();
        buttonCompareTab3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblCompare2 = new javax.swing.JLabel();
        comboCompareTab3 = new javax.swing.JComboBox<>();
        comboSameTab3 = new javax.swing.JComboBox<>();
        lblCompare3 = new javax.swing.JLabel();
        comboLookTab3 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstThreadTimeTab3 = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        labelThreadTab3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        lstThreadTab3 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Big Data");

        pane1.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        lblThread.setText("THREAD SAYISINI GİRİNİZ:");

        textThread.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textThread.setToolTipText("");
        textThread.setAutoscrolls(false);
        textThread.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textThreadActionPerformed(evt);
            }
        });

        lblCompareTo.setText("KARŞILAŞTIRMA YÜZDESİNİ GİRİNİZ");

        textCompare.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textCompare.setToolTipText("");
        textCompare.setAutoscrolls(false);
        textCompare.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCompareActionPerformed(evt);
            }
        });

        buttonCompare.setText("KARŞILAŞTIR");
        buttonCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompareActionPerformed(evt);
            }
        });

        lblCompare.setText("KARŞILAŞTIRMAK İSTEDİĞİNİZ ALAN");

        comboCompare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product", "İssue", "Company", "State", "Zip Code", "Complaint ID" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textCompare, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCompare)
                            .addComponent(comboCompare, 0, 198, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(55, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThread, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textThread, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCompareTo, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCompare, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCompare)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboCompare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThread)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textThread, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCompareTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textCompare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(buttonCompare, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jLabel5.setText("Toplam Thread Süresi:");

        jScrollPane1.setViewportView(lstThreadTime);

        jScrollPane3.setViewportView(lstThread1);

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(labelThread, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelThread, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1393, 1393, 1393))))
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addGap(504, 504, 504)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(9, Short.MAX_VALUE)))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelThread, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3)
                    .addContainerGap()))
        );

        pane1.addTab("tab1", jDesktopPane1);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        lblThread1.setText("THREAD SAYISINI GİRİNİZ:");

        textThreadTab2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textThreadTab2.setToolTipText("");
        textThreadTab2.setAutoscrolls(false);
        textThreadTab2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textThreadTab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textThreadTab2ActionPerformed(evt);
            }
        });

        lblCompareTo1.setText("KARŞILAŞTIRMA YÜZDESİNİ GİRİNİZ");

        textCompareTab2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textCompareTab2.setToolTipText("");
        textCompareTab2.setAutoscrolls(false);
        textCompareTab2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textCompareTab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCompareTab2ActionPerformed(evt);
            }
        });

        buttonCompareTab2.setText("KARŞILAŞTIR");
        buttonCompareTab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompareTab2ActionPerformed(evt);
            }
        });

        jLabel1.setText("COMPLAİNT ID:");

        textComplaintTab2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textComplaintTab2.setToolTipText("");
        textComplaintTab2.setAutoscrolls(false);
        textComplaintTab2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textComplaintTab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textComplaintTab2ActionPerformed(evt);
            }
        });

        lblCompare1.setText("KARŞILAŞTIRMAK İSTEDİĞİNİZ ALAN");

        comboCompareTab2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product", "İssue", "Company", "State", "Zip Code", "Complaint ID" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThread1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(textComplaintTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)))
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCompareTo1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCompare1))
                        .addContainerGap(74, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(textThreadTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(textComplaintTab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCompare1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCompareTo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThread1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textThreadTab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCompareTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(lstThreadTimeTab2);

        jLabel6.setText("Toplam Thread Süresi:");

        jScrollPane4.setViewportView(lstThreadTab2);

        jDesktopPane2.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(labelThreadTab2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jScrollPane4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelThreadTab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                                .addGap(12, 12, 12))
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelThreadTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        pane1.addTab("tab2", jDesktopPane2);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
        );

        lblThread2.setText("THREAD SAYISINI GİRİNİZ:");

        textThreadTab3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textThreadTab3.setToolTipText("");
        textThreadTab3.setAutoscrolls(false);
        textThreadTab3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textThreadTab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textThreadTab3ActionPerformed(evt);
            }
        });

        lblCompareTo2.setText("KARŞILAŞTIRMA YÜZDESİNİ GİRİNİZ");

        textCompareTab3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textCompareTab3.setToolTipText("");
        textCompareTab3.setAutoscrolls(false);
        textCompareTab3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textCompareTab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCompareTab3ActionPerformed(evt);
            }
        });

        buttonCompareTab3.setText("KARŞILAŞTIR");
        buttonCompareTab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompareTab3ActionPerformed(evt);
            }
        });

        jLabel2.setText("AYNI OLACAK ALANI SEÇİNİZ");

        lblCompare2.setText("GÖSTERMEK İSTEDİĞİNİZ ALAN");

        comboCompareTab3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product", "İssue", "Company", "State", "Zip Code", "Complaint ID" }));

        comboSameTab3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product", "İssue", "Company", "State", "Zip Code", "Complaint ID" }));

        lblCompare3.setText("KARŞILAŞTIRMAK İSTEDİĞİNİZ ALAN");

        comboLookTab3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product", "İssue", "Company", "State", "Zip Code", "Complaint ID" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCompareTo2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCompare3)
                            .addComponent(comboLookTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboSameTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCompare2))
                        .addContainerGap(61, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThread2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textThreadTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(comboSameTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblCompare3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCompare2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboLookTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCompareTo2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(textCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblThread2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textThreadTab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCompareTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );

        jScrollPane5.setViewportView(lstThreadTimeTab3);

        jLabel7.setText("Toplam Thread Süresi:");

        jScrollPane6.setViewportView(lstThreadTab3);

        jDesktopPane3.setLayer(jPanel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane3.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane3.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane3.setLayer(labelThreadTab3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane3.setLayer(jScrollPane6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane3Layout = new javax.swing.GroupLayout(jDesktopPane3);
        jDesktopPane3.setLayout(jDesktopPane3Layout);
        jDesktopPane3Layout.setHorizontalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(labelThreadTab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jDesktopPane3Layout.setVerticalGroup(
            jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelThreadTab3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(jDesktopPane3Layout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addContainerGap())))
        );

        pane1.addTab("tab3", jDesktopPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1512, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pane1)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1540, 783));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCompareTab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompareTab3ActionPerformed

        lstThreadTimeTab3.removeAll();
        lstThreadTab3.removeAll();
        model = new DefaultListModel();
        ArrayList<Integer> dizi = new ArrayList();
        int compareTo = 0;
        int sameTo = 0;
        int lookTo = 0;
        String same = comboSameTab3.getItemAt(comboSameTab3.getSelectedIndex());

        switch (same) {

            case "Product":
                sameTo = 1;
                break;
            case "İssue":
                sameTo = 2;
                break;
            case "Company":
                sameTo = 3;
                break;
            case "State":
                sameTo = 4;
                break;
            case "Zip Code":
                sameTo = 5;
                break;
            case "Complaint ID":
                sameTo = 6;
                break;
            default:
            // code block
        }
        String data = comboCompareTab3.getItemAt(comboCompareTab3.getSelectedIndex());
        switch (data) {

            case "Product":
                compareTo = 1;
                break;
            case "İssue":
                compareTo = 2;
                break;
            case "Company":
                compareTo = 3;
                break;
            case "State":
                compareTo = 4;
                break;
            case "Zip Code":
                compareTo = 5;
                break;
            case "Complaint ID":
                compareTo = 6;
                break;
            default:
            // code block
        }

        String look = comboLookTab3.getItemAt(comboLookTab3.getSelectedIndex());
        switch (look) {

            case "Product":
                lookTo = 1;
                break;
            case "İssue":
                lookTo = 2;
                break;
            case "Company":
                lookTo = 3;
                break;
            case "State":
                lookTo = 4;
                break;
            case "Zip Code":
                lookTo = 5;
                break;
            case "Complaint ID":
                lookTo = 6;
                break;
            default:
            // code block
        }
        System.out.println("same : " + same + " same to:" + sameTo);
        System.out.println("look: " + look + " look to:" + lookTo);
        System.out.println("data: " + data + " compare to:" + compareTo);

        String incomingThread = textThreadTab3.getText();
        String incomingCompare = textCompareTab3.getText();
        int numOfThreads = Integer.parseInt(incomingThread);
        int compare = Integer.parseInt(incomingCompare);

        try {

            List<Tab3> threads = new ArrayList<>();

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numOfThreads; i++) {
                Tab3 tab3 = new Tab3(nations, compareTo, compare, sameTo, lookTo);
                tab3.start();
                threads.add(tab3);
            }

            threads.forEach(thr -> {
                try {
                    thr.join();
                    thr.interrupt();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            long time = System.currentTimeMillis() - startTime; // toplam geçen zaman

            threads.forEach(thr -> {
                try {
                    model.addElement(thr.getName() + " " + thr.totalTime);
                    lstThreadTimeTab3.setModel(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            String tm = String.valueOf(time);
            labelThreadTab3.setText(tm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_buttonCompareTab3ActionPerformed

    private void textCompareTab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCompareTab3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCompareTab3ActionPerformed

    private void textThreadTab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textThreadTab3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textThreadTab3ActionPerformed

    private void textComplaintTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textComplaintTab2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textComplaintTab2ActionPerformed

    private void buttonCompareTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompareTab2ActionPerformed
        lstThreadTimeTab2.removeAll();
        lstThreadTab2.removeAll();
        model = new DefaultListModel();

        int compareTo = 0;

        String data = comboCompareTab2.getItemAt(comboCompareTab2.getSelectedIndex());
        switch (data) {

            case "Product":
                compareTo = 1;
                break;
            case "İssue":
                compareTo = 2;
                break;
            case "Company":
                compareTo = 3;
                break;
            case "State":
                compareTo = 4;
                break;
            case "Zip Code":
                compareTo = 5;
                break;
            case "Complaint ID":
                compareTo = 6;
                break;
            default:
            // code block
        }

        String complaint = textComplaintTab2.getText();
        String incomingData = textThreadTab2.getText();
        String incomingCompare = textCompareTab2.getText();
        int numOfThreads = Integer.parseInt(incomingData);
        int compare = Integer.parseInt(incomingCompare);

        try {
 List<Tab2> threads = new ArrayList<>();

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numOfThreads; i++) {
                Tab2 th = new Tab2(nations, compareTo, compare, complaint);
                th.start();
                threads.add(th);
            }

            threads.forEach(thr -> {
                try {
                    thr.join();
                    thr.interrupt();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            long time = System.currentTimeMillis() - startTime; // toplam geçen zaman

            threads.forEach(thr -> {
                try {
                    model.addElement(thr.getName() + " " + thr.totalTime);
                    lstThreadTimeTab2.setModel(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            String tm = String.valueOf(time);
            labelThreadTab2.setText(tm);

           
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_buttonCompareTab2ActionPerformed

    private void textCompareTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCompareTab2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCompareTab2ActionPerformed

    private void textThreadTab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textThreadTab2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textThreadTab2ActionPerformed

    private void buttonCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompareActionPerformed
        lstThreadTime.removeAll();
        lstThread1.removeAll();
        model = new DefaultListModel();
        ArrayList<Integer> dizi = new ArrayList();
        int compareTo = 0;
        String data = comboCompare.getItemAt(comboCompare.getSelectedIndex());
        switch (data) {

            case "Product":
                compareTo = 1;
                break;
            case "İssue":
                compareTo = 2;
                break;
            case "Company":
                compareTo = 3;
                break;
            case "State":
                compareTo = 4;
                break;
            case "Zip Code":
                compareTo = 5;
                break;
            case "Complaint ID":
                compareTo = 6;
                break;
            default:
        }

        String incomingData = textThread.getText();
        String incomingCompare = textCompare.getText();
        int numOfThreads = Integer.parseInt(incomingData);
        int compare = Integer.parseInt(incomingCompare);

        try {

            List<Tab1> threads = new ArrayList<>();

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numOfThreads; i++) {
                Tab1 th = new Tab1(nations, compareTo, compare);
                th.start();
                threads.add(th);
            }

            threads.forEach(thr -> {
                try {
                    thr.join();
                    thr.interrupt();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            long time = System.currentTimeMillis() - startTime; // toplam geçen zaman

            threads.forEach(thr -> {
                try {
                    model.addElement(thr.getName() + " " + thr.totalTime);
                    lstThreadTime.setModel(model);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            String tm = String.valueOf(time);
            labelThread.setText(tm);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_buttonCompareActionPerformed

    private void textCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCompareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCompareActionPerformed

    private void textThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textThreadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textThreadActionPerformed
// </editor-fold>                        
// </editor-fold>                        
// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Swing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Swing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Swing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Swing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Swing().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Swing.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCompare;
    private javax.swing.JButton buttonCompareTab2;
    private javax.swing.JButton buttonCompareTab3;
    private javax.swing.JComboBox<String> comboCompare;
    private javax.swing.JComboBox<String> comboCompareTab2;
    private javax.swing.JComboBox<String> comboCompareTab3;
    private javax.swing.JComboBox<String> comboLookTab3;
    private javax.swing.JComboBox<String> comboSameTab3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDesktopPane jDesktopPane3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelThread;
    private javax.swing.JLabel labelThreadTab2;
    private javax.swing.JLabel labelThreadTab3;
    private javax.swing.JLabel lblCompare;
    private javax.swing.JLabel lblCompare1;
    private javax.swing.JLabel lblCompare2;
    private javax.swing.JLabel lblCompare3;
    private javax.swing.JLabel lblCompareTo;
    private javax.swing.JLabel lblCompareTo1;
    private javax.swing.JLabel lblCompareTo2;
    private javax.swing.JLabel lblThread;
    private javax.swing.JLabel lblThread1;
    private javax.swing.JLabel lblThread2;
    private javax.swing.JList<String> lstThread1;
    private javax.swing.JList<String> lstThreadTab2;
    private javax.swing.JList<String> lstThreadTab3;
    private javax.swing.JList<String> lstThreadTime;
    private javax.swing.JList<String> lstThreadTimeTab2;
    private javax.swing.JList<String> lstThreadTimeTab3;
    private javax.swing.JTabbedPane pane1;
    private javax.swing.JTextField textCompare;
    private javax.swing.JTextField textCompareTab2;
    private javax.swing.JTextField textCompareTab3;
    private javax.swing.JTextField textComplaintTab2;
    private javax.swing.JTextField textThread;
    private javax.swing.JTextField textThreadTab2;
    private javax.swing.JTextField textThreadTab3;
    // End of variables declaration//GEN-END:variables
}
