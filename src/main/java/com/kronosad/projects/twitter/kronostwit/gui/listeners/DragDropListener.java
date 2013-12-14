package com.kronosad.projects.twitter.kronostwit.gui.listeners;

import com.kronosad.projects.twitter.kronostwit.gui.windows.popup.WindowNewTweet;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

/**
 * User: russjr08
 * Date: 12/13/13
 * Time: 9:01 PM
 */
public class DragDropListener implements DropTargetListener {

    private WindowNewTweet tweetWindow;

    public DragDropListener(WindowNewTweet tweetWindow){
        this.tweetWindow = tweetWindow;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {}

    @Override
    public void dragOver(DropTargetDragEvent dropTargetDragEvent) {}

    @Override
    public void dropActionChanged(DropTargetDragEvent dropTargetDragEvent) {}

    @Override
    public void dragExit(DropTargetEvent dropTargetEvent) {}

    @Override
    public void drop(DropTargetDropEvent event) {
        // Accept copy drops
        event.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    List files = (List) transferable.getTransferData(flavor);

                    // Loop them through
//                    for (File file : files) {
//
//                        // Print out the file path
//                        System.out.println("File path is '" + file.getPath() + "'.");
//
//                    }
                    for(int i = 0; i < files.size(); i++){
                        File file = (File)files.get(i);
                        System.out.println("Found File at: " + file.getPath());
                        tweetWindow.setAttachment(file);
                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }

        // Inform that the drop is complete
        event.dropComplete(true);
    }
}
