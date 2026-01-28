package adapter;

class TypeCToUSBAdapter implements USB {
    private TypeCCharger typeCCharger;

    public TypeCToUSBAdapter(TypeCCharger charger) {
        this.typeCCharger = charger;
    }

    @Override
    public void connectUSB() {
        typeCCharger.connectTypeC();
    }
}
