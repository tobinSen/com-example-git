package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

import java.util.List;

public class GsonDemo {

    private final static Gson gson;

    static {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }


    public static void main(String[] args) {
        JsonObject object = new JsonObject();
        object.addProperty("name","tom");
        object.addProperty("age", 12);
        JsonArray array = new JsonArray();
        array.add(object);
        array.add(object);
        System.out.println(array);
        System.out.println(gson.toJson(array));


    }


    public static class User {

        @Expose
        public String name;
        public int age;
        public String emailAddress;

        public List<Animal> animalList;
        private Animal animal;

        public User(String name, int age, String emailAddress) {
            this.name = name;
            this.age = age;
            this.emailAddress = emailAddress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public List<Animal> getAnimalList() {
            return animalList;
        }

        public void setAnimalList(List<Animal> animalList) {
            this.animalList = animalList;
        }

        public Animal getAnimal() {
            return animal;
        }

        public void setAnimal(Animal animal) {
            this.animal = animal;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", emailAddress='" + emailAddress + '\'' +
                    ", animalList=" + animalList +
                    ", animal=" + animal +
                    '}';
        }
    }

    public static class Animal {
        public String name;
        public int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Animal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Animal{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
