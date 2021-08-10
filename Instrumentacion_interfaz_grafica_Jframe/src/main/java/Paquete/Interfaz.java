package Paquete;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


public class Interfaz extends javax.swing.JFrame {
    
    DecimalFormat df = new DecimalFormat("#.##");
    
    public Interfaz() {
        initComponents();
        imaganes();
        init_ino();
    }
    
    private static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                    //Se imprime el mensaje recibido en la consola
                    //System.out.println(ino.printMessage());
                    String dat = ""+ino.printMessage();
                    dale(dat);
                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };
    
    public void init_ino()
    {
        try {
            ino.arduinoRX("COM3", 9600, listener);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dale(String data)
    {
        int A1,A2,A3;
        
        //System.out.println(data);
        A1 = leer_trama(data,0);
        NTC(A1);
        
        A2 = leer_trama(data,1);
        System.out.println(A2);
        Hs(A2);
        
        A3 = leer_trama(data,2);
        Fotoresistor(A3);
        
        
        
    }
    
    //CODIGO POR ANALIZAR **********************
    public int leer_trama(String linea, int señal){
        
        int i;
        float v1,v2,v3;
        float[] señales = null;
        String A1 = null;
        String A2 = null;
        String A3 = null;
        
        
        for(i=0; i < linea.length(); i++){
            A1 = "";
            A2 = "";
            A3 = "";
                    //SEÑAL A1
                    if(linea.charAt(i)=='('){
                        i++;
                       for(i=i; i < linea.length(); i++){
                           if(linea.charAt(i)!=')')
                               A1 += linea.charAt(i);
                           else{
                               int vol1 = Integer.parseInt(A1);
                               if(señal==0){
                                   return vol1;
                               }
                               break;
                           }
                       }
                       
                    }else 
                    
                    //SEÑAL A2
                    if(linea.charAt(i)=='['){
                        i++;
                       for(i=i; i < linea.length(); i++){
                           if(linea.charAt(i)!=']')
                               A2 += linea.charAt(i);
                           else{
                               int vol2 = Integer.parseInt(A2);
                               if(señal==1){
                                   return vol2;
                               }
                               break;
                           }
                       }
                    } else 
                    
                    //SEÑAL A3
                        if(linea.charAt(i)=='{'){
                        i++;
                       for(i=i; i < linea.length(); i++){
                           if(linea.charAt(i)!='}')
                               A3 += linea.charAt(i);
                           else{
                               int vol3 = Integer.parseInt(A3);
                               if(señal==2){
                                   return vol3;
                               }
                               break;
                           }
                       }
                    } 
                }  
        return señal;
    }
    
    
    
    //*****************
    
    //IMAGENES
    
    public void imaganes(){
        
        //LOGO IPN
        ImageIcon icono = new ImageIcon(getClass().getResource("logo_ipn.png"));
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(250,250,Image.SCALE_SMOOTH));
        jLabel1.setIcon(iconoEscalado);
        
        //LOGO ESCOM
        ImageIcon icono1 = new ImageIcon(getClass().getResource("logo_escom.jpg"));
        Image imagen1 = icono1.getImage();
        ImageIcon iconoEscalado1 = new ImageIcon (imagen1.getScaledInstance(250,250,Image.SCALE_SMOOTH));
        jLabel2.setIcon(iconoEscalado1);
        
        ImageIcon icono2 = new ImageIcon(getClass().getResource("fondo.png"));
        Image imagen2 = icono2.getImage();
        ImageIcon iconoEscalado2 = new ImageIcon (imagen2.getScaledInstance(1750,1300,Image.SCALE_SMOOTH));
        jLabel35.setIcon(iconoEscalado2);
        
        //ESTA RUTA 
    }
    
    public void NTC(int dato){
        
        float vo = (float) ((float) ((dato*3.56)/1023)+0.095);
        float vop = (float) ((vo)/3.286);
        float temp = (float) ((vo*40)/3.3 + 20);
        
        String vos = Float.toString(vo);
        String vops = Float.toString(vop);
        String temps = Float.toString(temp);
        

        jLabel8.setText(df.format(vo)+" V");
        jLabel9.setText(df.format(vop)+" V");
        jLabel10.setText(df.format(temp)+" °C");
        
        int temperatura = (int) temp;
        
        switch(temperatura)
        {
            case 20:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp2.jpg"))); break;
            case 21:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp2.jpg"))); break;
            case 22:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp2.jpg"))); break;
            case 23:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp2.jpg"))); break;
            case 24:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp2.jpg"))); break;
            case 25:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp3.jpg"))); break;
            case 26:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp3.jpg"))); break;
            case 27:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp3.jpg"))); break;
            case 28:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp3.jpg"))); break;
            case 29:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp3.jpg"))); break;
            case 30:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp4.jpg")));break;
            case 31:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp4.jpg")));break;
            case 32:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp4.jpg")));break;
            case 33:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp4.jpg")));break;
            case 34:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp4.jpg")));break;
            case 35:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp5.jpg")));break;
            case 36:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp5.jpg")));break;
            case 37:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp5.jpg")));break;
            case 38:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp5.jpg")));break;
            case 39:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp5.jpg")));break;
            case 40:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp6.jpg")));break;
            case 41:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp6.jpg")));break;
            case 42:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp6.jpg")));break;
            case 43:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp6.jpg")));break;
            case 44:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp6.jpg")));break;
            case 45:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp7.jpg")));break;
            case 46:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp7.jpg")));break;
            case 47:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp7.jpg")));break;
            case 48:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp7.jpg")));break;
            case 49:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp7.jpg")));break;
            case 50:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp8.jpg")));break;
            case 51:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp8.jpg")));break;
            case 52:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp8.jpg")));break;
            case 53:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp8.jpg")));break;
            case 54:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp8.jpg")));break;
            case 55:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp9.jpg")));break;
            case 56:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp9.jpg")));break;
            case 57:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp9.jpg")));break;
            case 58:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp9.jpg")));break;
            case 59:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp9.jpg")));break;
            case 60:jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("temp10.jpg")));break;
            
        }
        
        
        
    }
    
    public void Hs(int dato){
        
        
        float vo = (float) ((float) ((dato*3.56)/1023)+0.2);
        float vop = (float) ((vo+6.7)/1.157);
        float fre = (float) ((vop)/0.0011764);
        float hum = (float) ((3.3 - vo)*55.742);
        
        String vos = Float.toString(vo);
        String vops = Float.toString(vop);
        String fres = Float.toString(fre);
        String hums = Float.toString(hum);
        

        jLabel17.setText(df.format(vo)+" V");
        jLabel18.setText(df.format(vop)+" V");
        jLabel19.setText(df.format(fre)+" Hz");
        jLabel20.setText(df.format(hum)+" %");
        
        int humedad = (int) hum;
        
         switch(humedad)
        {
            case 40:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 41:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 42:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 43:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 44:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 45:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 46:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum0.jpg"))); break;
            case 47:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 48:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 49:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 50:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 51:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 52:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum1.jpg"))); break;
            case 53:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 54:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 55:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 56:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 57:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 58:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum2.jpg"))); break;
            case 59:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 60:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 61:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 62:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 63:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 64:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum3.jpg"))); break;
            case 65:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 66:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 67:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 68:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 69:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 70:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum4.jpg"))); break;
            case 71:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 72:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 73:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 74:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 75:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 76:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum5.jpg"))); break;
            case 77:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 78:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 79:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 80:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 81:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 82:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum6.jpg"))); break;
            case 83:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 84:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 85:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 86:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 87:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 88:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum7.jpg"))); break;
            case 89:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 90:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 91:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 92:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 93:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 94:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum8.jpg"))); break;
            case 95:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            case 96:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            case 97:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            case 98:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            case 99:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            case 100:jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("hum9.jpg"))); break;
            
            
        }
        
    }
    
    public void Fotoresistor(int dato){
        
        int[] valores1 = {5000000,2600,2140,1790,936,627,600};
        int[] valores2 = {3000,4145,3540,2620,1300,888,666};
        int[] focos = {0,25,40,60,75,100};
        float lumens = 0;
        
        float vo = (float) ((float) ((dato*3.56)/1023)+0.2);
        float vop = (float) ((vo)/4.881);
        float fre = (float) ((vop)/0.0011143);
        float res = (float) ((float) (667284.5227/fre)-499.5);
        
        String vos = Float.toString(vo);
        String vops = Float.toString(vop);
        String fres = Float.toString(fre);
        String ress = Float.toString(res);
        

        jLabel28.setText(df.format(vo)+" V");
        jLabel29.setText(df.format(vop)+" V");
        jLabel30.setText(df.format(fre)+" Hz");
        jLabel31.setText(df.format(res)+" ohm");
        
        
        
        if(res>=valores1[0]+100){
            jLabel32.setText(focos[0]+" w");
            lumens = (focos[0]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum1.jpg")));
        } else 
            if(res>=valores1[1]+100){
            jLabel32.setText(focos[1]+" w");
            lumens = (focos[1]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum2.jpg")));
        } else 
                if(res>=valores1[2]+100){
                    jLabel32.setText(focos[2]+" w");
                    lumens = (focos[2]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum3.jpg")));
        } else 
                    if(res>=valores1[3]+100){
                        jLabel32.setText(focos[3]+" w");
                        lumens = (focos[3]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum4.jpg")));
        } else 
                        if(res>=valores1[4]+100){
                            jLabel32.setText(focos[4]+" w");
                            lumens = (focos[4]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum5.jpg")));
        } else 
                            if(res>=valores1[5]+100){
                                jLabel32.setText(focos[5]+" w");
                                lumens = (focos[5]*1420)/100;
            jLabel33.setText(lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum6.jpg")));
        } else
            if(res>=valores1[5]-100){
                                jLabel32.setText("+"+focos[5]+" w");
                                lumens = (focos[5]*1420)/100;
            jLabel33.setText("+"+lumens+" lumens");
            jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("lum7.jpg")));
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1800, 1500));
        setSize(new java.awt.Dimension(2000, 1500));
        getContentPane().setLayout(null);

        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(250, 250));
        jLabel1.setPreferredSize(new java.awt.Dimension(250, 250));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(26, 28, 250, 250);

        jLabel2.setText("jLabel2");
        jLabel2.setPreferredSize(new java.awt.Dimension(250, 250));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(1416, 28, 250, 250);

        jLabel3.setText("jLabel3");
        jLabel3.setPreferredSize(new java.awt.Dimension(150, 450));
        getContentPane().add(jLabel3);
        jLabel3.setBounds(256, 332, 150, 450);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel4.setText("Vo:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(185, 810, 54, 44);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setText("Vop:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(185, 892, 74, 44);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel6.setText("T:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(185, 977, 34, 44);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(379, 810, 244, 44);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel9.setText("jLabel9");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(379, 892, 207, 44);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel10.setText("jLabel10");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(379, 977, 193, 44);

        jLabel12.setText("jLabel12");
        jLabel12.setPreferredSize(new java.awt.Dimension(350, 350));
        getContentPane().add(jLabel12);
        jLabel12.setBounds(638, 383, 350, 350);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel13.setText("Vo:");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(681, 761, 54, 44);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel14.setText("Vo331");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(681, 843, 101, 44);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel15.setText("Fre:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(681, 928, 64, 44);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel16.setText("Hum:");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(681, 1000, 87, 44);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel17.setText("jLabel17");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(840, 760, 230, 44);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel18.setText("jLabel18");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(846, 843, 220, 44);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel19.setText("jLabel19");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(846, 928, 200, 44);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel20.setText("jLabel20");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(846, 1000, 210, 44);

        jLabel21.setText("jLabel21");
        jLabel21.setPreferredSize(new java.awt.Dimension(350, 350));
        getContentPane().add(jLabel21);
        jLabel21.setBounds(1131, 383, 350, 350);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel22.setText("Vo:");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(1149, 761, 54, 44);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel23.setText("Vo331");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(1131, 869, 101, 44);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel24.setText("Fre:");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(1131, 954, 64, 44);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel25.setText("Res: ");
        getContentPane().add(jLabel25);
        jLabel25.setBounds(1131, 1026, 81, 44);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel26.setText("Pot: ");
        getContentPane().add(jLabel26);
        jLabel26.setBounds(1131, 1110, 76, 44);

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel27.setText("Lumens: ");
        getContentPane().add(jLabel27);
        jLabel27.setBounds(1131, 1182, 147, 44);

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel28.setText("jLabel28");
        getContentPane().add(jLabel28);
        jLabel28.setBounds(1357, 761, 200, 44);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel29.setText("jLabel29");
        getContentPane().add(jLabel29);
        jLabel29.setBounds(1365, 869, 250, 44);

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel30.setText("jLabel30");
        getContentPane().add(jLabel30);
        jLabel30.setBounds(1365, 954, 260, 44);

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel31.setText("jLabel31");
        getContentPane().add(jLabel31);
        jLabel31.setBounds(1365, 1026, 270, 44);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel32.setText("jLabel32");
        getContentPane().add(jLabel32);
        jLabel32.setBounds(1365, 1110, 230, 44);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel33.setText("jLabel33");
        getContentPane().add(jLabel33);
        jLabel33.setBounds(1365, 1182, 240, 44);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel34.setText("Proyecto instrumentación grupo 3CM1");
        getContentPane().add(jLabel34);
        jLabel34.setBounds(441, 116, 807, 58);

        jLabel35.setText("jLabel35");
        jLabel35.setPreferredSize(new java.awt.Dimension(1750, 1300));
        getContentPane().add(jLabel35);
        jLabel35.setBounds(0, 0, 2000, 1260);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
