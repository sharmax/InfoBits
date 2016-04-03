package com.example.suryansh.infobits;

import android.app.Dialog;
import android.os.Bundle;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;
import android.text.Html;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ebooks extends homepage implements OnClickListener{

    Dialog dialog;
   @Override
   protected void onCreate (Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.ebooks);
       TextView ebscoLink = (TextView) findViewById(R.id.ebscoClickHere);
       ebscoLink.setText(Html.fromHtml("<a href=\"http://www.google.com\">CLICK HERE</a>"));
       ebscoLink.setMovementMethod(LinkMovementMethod.getInstance());

   }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ebsco:
                Intent ebscoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bits-pilani.ac.in:12354/pdf/EBSCO%20eBooks%20Mannual.pdf"));
                startActivity(ebscoIntent);
                break;

            case R.id.taylorFranics:
                Intent TFIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tandfebooks.com/page/openaccess#listofOAtitles"));
                startActivity(TFIntent);
                break;

            case R.id.springer:
                Intent springerIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://link.springer.com/search/page/3?showAll=false&facet-content-type=%22Book%22"));
                startActivity(springerIntent);
                break;

            case R.id.openAccess:
                Intent openIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.oapen.org/search?sort=year;f1-language=English"));
                startActivity(openIntent);
                break;

            case R.id.pearsonEducation:
                Intent i = new Intent(ebooks.this, downloadable_links.class);
                startActivity(i);
                break;
            case R.id.intech:
                Intent intIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.intechopen.com/books"));
                startActivity(intIntent);
                break;
            case R.id.openTB:
                Intent openTBIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://open.umn.edu/opentextbooks/"));
                startActivity(openTBIntent);
                break;
            case R.id.scienceDirect:
                Intent scienceIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.bits-pilani.ac.in:12354/index.php"));
                startActivity(scienceIntent);
                break;
            default:
                Intent defaultIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.bits-pilani.ac.in:12354/index.php"));
                startActivity(defaultIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        else{
            super.onBackPressed();
        }
    }

   }

