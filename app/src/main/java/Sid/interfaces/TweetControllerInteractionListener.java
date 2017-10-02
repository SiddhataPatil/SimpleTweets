package Sid.interfaces;

import android.view.View;

import Sid.models.TweetListModel;
import Sid.util.SnackbarUtil;

public interface TweetControllerInteractionListener {
    void startAction(TweetListModel.PageType page, String pageTypeRefId);

    void showError(SnackbarUtil.SnackbarErrorType type, View.OnClickListener listener);
}
