package br.com.notificationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.notificationapp.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        notificationManager = NotificationManagerCompat.from( this );

        textViewResult = findViewById( R.id.text_view_result );


    }

    public void sendComment(View v){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/typicode/demo/")
                .addConverterFactory( GsonConverterFactory.create() )
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create( JsonPlaceHolderApi.class );

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments();

        call.enqueue( new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText( "code: " + response.code() );
                    return;
                }
                List<Comment> comments = response.body();

                for (Comment comment : comments){

                    String content = "";
                    content += "Comment:" + "\n";
                    content += "ID: " + comment.getId() + "\n";
                    content += "ID post: " + comment.getPostId() + "\n";
                    content += "Body: " + comment.getBody() + "\n";
                    content += " " + "\n";
                    content += "------------------------" + "\n";

                    textViewResult.append( content );
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText( t.getMessage() );
            }
        } );

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                    .setSmallIcon( R.drawable.ic_comment )
                    .setContentTitle( "Comments" )
                    .setContentText( "All the comments in app" )
                    .setPriority( NotificationCompat.PRIORITY_HIGH )
                    .setCategory( NotificationCompat.CATEGORY_MESSAGE )
                    .build();

            notificationManager.notify( 1, notification );

    }

}
