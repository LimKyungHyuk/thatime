package net.khim.thatime.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import net.khim.thatime.NMapPOIflagType;
import net.khim.thatime.NMapViewerResourceProvider;
import net.khim.thatime.R;

public class FragmentMap extends Fragment {

    private NMapContext mMapContext;
    private NMapView mMapView;
    private NMapController mMapController;

    private NMapViewerResourceProvider mMapViewerResourceProvider;

    private static final String CLIENT_ID = "fxfvlk0ljYSQUfFP0UWu";// 애플리케이션 클라이언트 아이디 값
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_map, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (NMapView)getView().findViewById(R.id.mapView);
        mMapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mMapContext.setupMapView(mMapView);
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();

        mMapView.setClickable(true);
        mMapController = mMapView.getMapController();
        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);

        //Context a = (Context)mMapContext;

        mMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());

        NMapOverlayManager mOverlayManager = new NMapOverlayManager(getActivity(), mMapView,
                mMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;

        // POI data 설정
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(128.624389, 36.838966, "공기 좋고 물 맑은 도시", markerId, 0);

        poiData.endPOIdata();

        // POI data overlay 생성
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager
                .createPOIdataOverlay(poiData, null);

        poiDataOverlay.showFocusedItemOnly();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }


    NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {

        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {

        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

            NGeoPoint point = nMapView.getMapProjection().fromPixels((int)motionEvent.getX(),(int)motionEvent.getY());

            Toast.makeText(getActivity(), "경도:" + point.longitude + ", 위도:" + point.latitude , Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

        }


    };

    NMapPOIdataOverlay.OnStateChangeListener onStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener
            (){
        @Override
        public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

        }

        @Override
        public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

        }
    };

    NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
            if (nMapError == null) { // success

                mMapController.setMapCenter(new NGeoPoint(128.624389, 36.838966), 7);
            }
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

        }

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {

        }

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {

        }

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

        }
    };


}