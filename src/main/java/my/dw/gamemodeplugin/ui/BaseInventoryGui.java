package my.dw.gamemodeplugin.ui;

public abstract class BaseInventoryGui extends InventoryGui {

    public BaseInventoryGui(final String name, final int inventorySize) {
        super(name, GuiType.UNIQUE, inventorySize);
    }

    @Override
    protected void clearInventory() {
        getInventory().clear();
    }

}
