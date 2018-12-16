package com.avanade.santander.fundos.presentation;

import static com.google.common.base.Preconditions.checkNotNull;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.avanade.santander.UseCase;
import com.avanade.santander.UseCaseHandler;
import com.avanade.santander.fundos.domain.model.Fundos;
import com.avanade.santander.fundos.domain.usecase.GetFundos;


public class FundosPresenter implements FundosContract.Presenter {


    private final FundosContract.View mFundosView;
    private final GetFundos mGetFundos;

    private final UseCaseHandler mUseCaseHandler;

    public FundosPresenter(@NonNull UseCaseHandler useCaseHandler,
                           @NonNull FundosContract.View fundosView,
                           @NonNull GetFundos getFundos) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mFundosView = checkNotNull(fundosView, "fundosView cannot be null!");
        mGetFundos = checkNotNull(getFundos, "getFundos cannot be null!");
        mFundosView.setPresenter(this);
    }

    @Override
    public void start() {
        /** Call USE_CASE -> GetFundos() = buscar dados de Fundos, em Json, para exibir na tela */
        refreshFundos();
    }

    public void refreshFundos() {

        // Exibe icone de loading até efetuar atualização
        mFundosView.setLoadingIndicator(true);


        GetFundos.RequestValues requestValue =
                new GetFundos.RequestValues(/* Não vamos passar nenhum parametro de request aqui */);

        mUseCaseHandler
                .execute(mGetFundos, requestValue,
                        new UseCase.UseCaseCallback<GetFundos.ResponseValue>() {
                            @Override
                            public void onSuccess(GetFundos.ResponseValue response) {
                                // The view may not be able to handle UI updates anymore
                                if (!mFundosView.isActive())
                                    return;

                                /** Solicita a view para mostrar a tela com dados de Fundos */
                                Fundos fundos = response.getFundos();
                                mFundosView.setLoadingIndicator(false);
                                mFundosView.desenhaTela(fundos);
                            }

                            @Override
                            public void onError() {
                                // The view may not be able to handle UI updates anymore
                                if (!mFundosView.isActive())
                                    return;

                                /** Solicita a view para mostrar mensagem de erro */
                                mFundosView.setLoadingIndicator(false);
                                mFundosView.showLoadingFundosError();
                            }
                        }
                )
        ;
    }

    @Override
    public void baixarInfo(String stringURL) {
        // TODO - call URL de Fundos.Screen.DownInfo.Data;
    }

    @Override
    public void investir() {
        // TODO - call Contato
    }


}