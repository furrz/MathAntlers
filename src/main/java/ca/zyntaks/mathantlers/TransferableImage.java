package ca.zyntaks.mathantlers;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

record TransferableImage(Image image) implements Transferable {
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor == DataFlavor.imageFlavor;
    }

    @NotNull
    @Override
    public Object getTransferData(DataFlavor flavor) {
        return image;
    }
}
