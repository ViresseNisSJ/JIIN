package com.triples.bf_skku;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends PagerAdapter {

    String buildingName=null;

    ImageView ImageElevatorLocation;

    LayoutInflater inflater;

    public void add(String data) {
        buildingName = data;
    }

    public CustomAdapter(LayoutInflater inflater){
        this.inflater=inflater;
    }

    @Override
    public int getCount() {
        Field[] fields = R.drawable.class.getFields();
        List<Integer> drawables = new ArrayList<Integer>();
        for (Field field : fields) {
            // Take only those with name starting with "fr"
            if (field.getName().startsWith(buildingName)) {
                drawables.add(1);
            }
        }

        return drawables.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view=null;
        view = inflater.inflate(R.layout.viewpager_childview, null);

        if(position==0)
        {
            /*건물입구 화살표*/

            ImageElevatorLocation= (ImageView)view.findViewById(R.id.elevatorLocation);
            ImageElevatorLocation.setImageResource(R.drawable.elevator_sign);
            ImageElevatorLocation.setX(100);
        }

        String resName = buildingName+"0";
        int resID = inflater.getContext().getResources().getIdentifier(resName, "drawable", inflater.getContext().getPackageName());
        int picture = resID+position;
        ImageView img=(ImageView)view.findViewById(R.id.img_viewpager_childimage);
        BitmapDrawable drawable = (BitmapDrawable) inflater.getContext().getResources().getDrawable(picture);
        Bitmap bitmap = drawable.getBitmap();
        img.setImageBitmap(bitmap);

        container.addView(view);

        //bitmap.recycle();

        return view;
    }

    public void elevator(ViewGroup container){
        View view=null;
        view = inflater.inflate(R.layout.viewpager_childview, null);
        //ImageView translateImageView=(ImageView)view.findViewById
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


}
