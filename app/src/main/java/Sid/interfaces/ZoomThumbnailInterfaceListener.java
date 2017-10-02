package Sid.interfaces;

import android.view.View;
import android.widget.ImageView;

public interface ZoomThumbnailInterfaceListener {
    ImageView getZoomImageView();

    void zoomImageFromThumb(final View thumbView);
}
