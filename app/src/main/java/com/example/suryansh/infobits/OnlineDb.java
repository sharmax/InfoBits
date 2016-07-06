package com.example.suryansh.infobits;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class OnlineDb extends homepage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_db);

        final String[] OnlineDb = {"","ACM", "AIP", "ACS", "APS","ACSE","ASME","Annual Reviews","Capitaline Plus", "CUP","EBSCO"
        ,"Emerald","HEDBIB","IEEE","IME","ISID","IOP","ISI Web", "J-Gate","JSTOR","MathSciNet","OUP","Portland Press",
                "Project Euclid","Project Muse","Proquest Online","RSC", "Springer","Science Direct","SciFinder","SIAM","Taylor & Francis","Wiley Interscience"};
        final String[] DbLinks = {"acm.org", "aip.org", "asme.org", "ieee.org"};
        setContentView(R.layout.activity_online_db);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, OnlineDb);
        ListView listView = (ListView) findViewById(R.id.onlineDb);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = String.valueOf(parent.getItemAtPosition(position));
                        String ilp = DbLinks[position];
                        //String link = DbLinks[position];
                        switch (name) {
                            case "ACM":
                                Intent acm = new Intent(Intent.ACTION_VIEW, Uri.parse("http://portal.acm.org/dl.cfm"));
                                startActivity(acm);
                                break;
                            case "AIP":
                                Intent aip = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aip.org"));
                                startActivity(aip);
                                break;
                            case "ACS":
                                Intent acs = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pubs.acs.org/about.html"));
                                startActivity(acs);
                                break;
                            case "APS":
                                Intent aps = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aps.org/"));
                                startActivity(aps);
                                break;
                            case "ASCE":
                                Intent asce = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ascelibrary.org/journals/all_journal_titles"));
                                startActivity(asce);
                                break;
                            case "ASME":
                                Intent asme = new Intent(Intent.ACTION_VIEW, Uri.parse("http://asmedigitalcollection.asme.org/journals.aspx"));
                                startActivity(asme);
                                break;
                            case "Annual Reviews":
                                Intent ar = new Intent(Intent.ACTION_VIEW, Uri.parse("http://arjournals.annualreviews.org/"));
                                startActivity(ar);
                                break;
                            case "Capitaline Plus":
                                Intent cp = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.capitaline.com/user/framepage.asp?id=1"));
                                startActivity(cp);
                                break;
                            case "CUP":
                                Intent cup = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.journals.cambridge.org/"));
                                startActivity(cup);
                                break;
                            case "EBSCO":
                                Intent ebsco = new Intent(Intent.ACTION_VIEW, Uri.parse("http://search.ebscohost.com/"));
                                startActivity(ebsco);
                                break;
                            case "Emerald":
                                Intent emerald = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.emeraldinsight.com/"));
                                startActivity(emerald);
                                break;
                            case "HEDBIB":
                                Intent hedbib = new Intent(Intent.ACTION_VIEW, Uri.parse("http://hedbib.iau-aiu.net/"));
                                startActivity(hedbib);
                                break;
                            case "IEEE":
                                Intent ieee= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ieee.org/ieeexplore/"));
                                startActivity(ieee);
                                break;
                            case "IME":
                                Intent ime= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bits-pilani.ac.in:12354/services/ime.php"));
                                startActivity(ime);
                                break;
                            case "ISID":
                                Intent isid= new Intent(Intent.ACTION_VIEW, Uri.parse("http://111.93.33.222/login3.asp"));
                                startActivity(isid);
                                break;
                            case "IOP":
                                Intent iop= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iop.org/EJ/"));
                                startActivity(iop);
                                break;
                            case "ISI Web":
                                Intent isi= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.isiknowledge.com/"));
                                startActivity(isi);
                                break;
                            case "J-Gate":
                                Intent jgate= new Intent(Intent.ACTION_VIEW, Uri.parse("http://jgateplus.com/search/"));
                                startActivity(jgate);
                                break;
                            case "JSTOR":
                                Intent jstor= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jstor.org/"));
                                startActivity(jstor);
                                break;
                            case "MathSciNet":
                                Intent msn= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ams.org/mathscinet/index.html"));
                                startActivity(msn);
                                break;
                            case "OUP":
                                Intent oup= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.oxfordjournals.org/en/"));
                                startActivity(oup);
                                break;
                            case "Portland Press":
                                Intent pp= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.portlandpress.com/"));
                                startActivity(pp);
                                break;
                            case "Project Euclid":
                                Intent pe= new Intent(Intent.ACTION_VIEW, Uri.parse("http://projecteuclid.org/"));
                                startActivity(pe);
                                break;
                            case "Project Muse":
                                Intent pm= new Intent(Intent.ACTION_VIEW, Uri.parse("http://muse.jhu.edu/"));
                                startActivity(pm);
                                break;
                            case "Proquest Online":
                                Intent po= new Intent(Intent.ACTION_VIEW, Uri.parse("http://search.proquest.com/?accountid=81487"));
                                startActivity(po);
                                break;
                            case "RSC":
                                Intent rsc= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.rsc.org/is/journals/current/ejs.htm"));
                                startActivity(rsc);
                                break;
                            case "Springer":
                                Intent springer= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.springerlink.com/journals/"));
                                startActivity(springer);
                                break;
                            case "Science Direct":
                                Intent sd= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sciencedirect.com/"));
                                startActivity(sd);
                                break;
                            case "SciFinder":
                                Intent sf= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bits-pilani.ac.in:12354/services/SciFinder_registration.php"));
                                startActivity(sf);
                                break;
                            case "SIAM":
                                Intent siam= new Intent(Intent.ACTION_VIEW, Uri.parse("http://epubs.siam.org/"));
                                startActivity(siam);
                                break;
                            case "Taylor & Franscis":
                                Intent tf= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.tandfonline.com/"));
                                startActivity(tf);
                                break;
                            case "Wiley Interscience":
                                Intent wi= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www3.interscience.wiley.com/cgi-bin/home"));
                                startActivity(wi);
                                break;

                            default:
                                Intent defaultIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://www.bits-pilani.ac.in:12354/index.php"));
                                startActivity(defaultIntent);
                                break;
                        }


                    }
                }
        );
    }
}
