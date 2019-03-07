package choi.ccb.com.fragmenttabhostdemo.video.videoplayer.visibility.scroll;


import choi.ccb.com.fragmenttabhostdemo.video.videoplayer.visibility.items.ListItem;

/**
 * This interface is used by
 * Using this class to get
 *
 * @author Wayne
 */
public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
