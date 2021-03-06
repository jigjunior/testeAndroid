package com.avanade.santander.data;

import android.support.annotation.NonNull;

import com.avanade.santander.contato.domain.model.Cell;
import com.avanade.santander.contato.domain.model.Formulario;

import java.util.List;

/**
 * Main entry point for accessing Cells data.
 * <p>
 * For simplicity, only getFormulario() and getCell() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new Cell is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface CellsDataSource {


    // CALLBACK datasource REMOTE
    interface LoadCellsCallback {

        void onCellsLoaded(Formulario formulario);

        void onDataNotAvailable();
    }

    void getFormulario(@NonNull LoadCellsCallback callback);

    void refreshCells(List<Cell> cells);

}
