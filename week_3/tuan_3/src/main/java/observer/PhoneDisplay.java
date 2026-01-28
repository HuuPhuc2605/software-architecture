package observer;


    class PhoneDisplay implements Observer {
        public void update(int temp) {
            System.out.println("Điện thoại cập nhật nhiệt độ: " + temp);
        }
    }

