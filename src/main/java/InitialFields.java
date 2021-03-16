public class InitialFields {
    private Field smallShipField;
    private Field mediumShipField;
    private Field largeShipField;
    private Field xlargeShipField;

    public InitialFields(Field smallShipField, Field mediumShipField, Field largeShipField, Field xlargeShipField){
        this.smallShipField = smallShipField;
        this.mediumShipField = mediumShipField;
        this.largeShipField = largeShipField;
        this.xlargeShipField = xlargeShipField;
    }

    public Field getSmallShipField() {
        return smallShipField;
    }

    public Field getMediumShipField() {
        return mediumShipField;
    }

    public Field getLargeShipField() {
        return largeShipField;
    }

    public Field getXlargeShipField() {
        return xlargeShipField;
    }
}
