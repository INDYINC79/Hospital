package hospital;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Hospital implements java.io.Serializable {

    public static void main(String[] args) {
        Patient[] patients = new Patient[3];
        patients[0] = new Patient("david", 99, "really sick");
        patients[1] = new Patient("nick", 199, "almost dead");
        patients[2] = new Patient("brian", 3, "tired");

        Care[] caretaker = new Care[3];
        caretaker[0] = new Doctor("bob", 55, "bones");
        caretaker[1] = new Nurse("jimmy", 44, "bedpans");
        caretaker[2] = new Doctor("burt", 77, "toes");

        double pRand = Math.floor(Math.random() * patients.length);
        double cRand = Math.floor(Math.random() * caretaker.length);

        Care c = caretaker[(int) cRand];
        Patient p = patients[(int) pRand];
        int temp = c.takeTemp(p);
        if (c instanceof Doctor) {
            Doctor d = (Doctor) c;
            System.out.println(d.name + " took the temp of " + p.name + " , his temp was " + temp + ".");
        } else {
            Nurse n = (Nurse) c;
            System.out.println(n.name + " took the temp of " + p.name + " , his temp was " + temp + ".");
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("serialized.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(c);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        

        Care deserializedCaregiver = null;
        
        try
        {
          FileInputStream inputFileStream = new FileInputStream("serialized.txt");
          ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
          deserializedCaregiver = (Care)objectInputStream.readObject();
          objectInputStream.close();
          inputFileStream.close();
        }
        catch(ClassNotFoundException e)
        {
          e.printStackTrace();
        }
        catch(IOException i)
        {
          i.printStackTrace();
        }
        System.out.println("Caregiver Deserialized !!! " + deserializedCaregiver);
      }
    }

class Person implements Serializable {
    public String name;
    public int age;
}

class Doctor extends Person implements Care {
    String specialty;

    public Doctor(String name, int age, String specialty) {
        this.name = name;
        this.age = age;
        this.specialty = specialty;
    }

    public String takeBP(Patient p) {
        return "here";
    }

    public String readChart(Patient p) {
        return "here2";
    }

    public int takeTemp(Patient p) {
        return 99;
    }
}

class Nurse extends Person implements Care {
    String specialty;

    public Nurse(String name, int age, String specialty) {
        this.name = name;
        this.age = age;
        this.specialty = specialty;
    }

    public String takeBP(Patient p) {
        return "here";
    }

    public String readChart(Patient p) {
        return "here2";
    }

    public int takeTemp(Patient p) {
        return 96;
    }
}

class Patient extends Person {
    String symptom;

    public Patient(String name, int age, String symptom) {
        this.name = name;
        this.age = age;
        this.symptom = symptom;
    }
}

interface Care {
    String takeBP(Patient p);

    String readChart(Patient p);

    int takeTemp(Patient p);
}
