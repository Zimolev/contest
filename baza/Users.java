package com.company.baza;

public class Users {

        private int id;
        private String name;
        private String login;
        private String password;



    public Users(String name, String loggin, String password) {
            this.id = id;
            this.name = name;
            this.login = loggin;
            this.password = password;
        }
        public String getPassword() {return password; }

        public int getId() {return id; }

        public String getName() {
            return name;
        }

        public String getLogin() {
            return login;
        }

        public void setId(int id) {            this.id = id;        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
