package com.amazon.android.contentbrowser.payments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.android.contentbrowser.R;
import com.amazon.android.model.content.Content;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.amazon.android.contentbrowser.ContentBrowser.PRICE_MAP;
import static com.amazon.android.contentbrowser.app.ContentBrowserApplication.GSON;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.HTTP_CLIENT;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.PAYTV_SERVER;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.createPayIdUrl;
import static com.amazon.android.contentbrowser.payments.PayIdHelper.getAddresses;

public class PaymentDialog {


    public static void createPayIdInputDialog(Activity context,
                                              Content content,
                                              DialogInterface.OnClickListener onClickListener)
            throws Exception {

        ViewGroup subView = (ViewGroup) context.getLayoutInflater().// inflater view
                inflate(R.layout.pay_id_input_dialog, null, false);

        TextView purchaseText = subView.findViewById(R.id.pay_id_text);
        purchaseText.setText(String.format(Locale.US, "Lending to: %s", content.getTitle());

        TextView conversionText = subView.findViewById(R.id.conversion_text);
        final String text = "Scan the QR code below to initiate the loan from a compatible mobile wallet. Your sender/wallet address will automatically be recorded as the lending address.";
        conversionText.setText(text);

        String finalBtcAddress = "3J98t1WpEZ73CNmQviecrnyiWrnqRhWNLy";
        Picasso picasso = new Picasso.Builder(context).downloader(new OkHttp3Downloader(HTTP_CLIENT)).build();
        picasso.setLoggingEnabled(true);
        new Handler(Looper.getMainLooper()).post(() -> {
            String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + finalBtcAddress;
            ImageView v = subView.findViewById(R.id.btcImage);
            picasso.load(url).into(v);

            new AlertDialog.Builder(context)
                    .setView(subView)
                    .setTitle("Scan address to complete loan")
                    .setPositiveButton("Done", onClickListener)
//                            .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();

        });


    }
}
