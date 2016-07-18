package coderoom1401.lawrence.imagebrowser.sample;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import coderoom1401.lawrence.imagebrowser.ImageBrowser;

public class MainActivity extends AppCompatActivity {
    private ImageBrowser mImageBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> imgList = new ArrayList<>();
        imgList.add("http://img3.imgtn.bdimg.com/it/u=1386248617,329841008&fm=21&gp=0.jpg");
        imgList.add("http://www.bz55.com/uploads/allimg/150616/140-1506160Z344-50.jpg");
        imgList.add("http://d.3987.com/song_150206/001.jpg");
        imgList.add("http://www.bz55.com/uploads/allimg/150605/139-150605153433-50.jpg");
        setContentView(R.layout.activity_main);
        mImageBrowser = (ImageBrowser) findViewById(R.id.imageBrowser);
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getApplicationContext());
                Glide.with(getApplicationContext()).load(imgList.get(position % imgList.size())).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        mImageBrowser.setPagerAdapter(adapter, imgList.size());

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageBrowser.startAutoScroll(ImageBrowser.DEFAULT_SCROLL_RATE, ImageBrowser.DEFAULT_SCROLL_SPEED);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageBrowser.stopAutoScroll();
            }
        });
    }
}
