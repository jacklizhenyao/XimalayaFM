package com.lanou.radiostation.bean;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class Recommend {
    public String name;
    public FocusImages focusImages;
    public EditorRecommendAlbums editorRecommendAlbums;
    public SpecialColumn specialColumn;
    public DiscoveryColumns discoveryColumns;
    public HotRecommends hotRecommends;

    public class HotRecommends{
        public List<Hot> list;
    }
    public class Hot{
        public List<Recommends> list;
        public String title;
    }
    public class Recommends{
        public String albumCoverUrl290;
        public String trackTitle;
        public String title;
        public String nickname;
        public int albumId;
        public int playsCounts;

    }

    public class FocusImages{
        public List<News> list;
    }
    public class DiscoveryColumns{
        public List<Discovery> list;
    }
    public class Discovery{
        public String coverPath;
        public String title;
        public String subtitle;

    }
    public class News{
        public String pic;
    }
    public class EditorRecommendAlbums{
        public List<Album> list;
    }
    public class Album{
        public String albumCoverUrl290;
        public String trackTitle;
        public String nickname;
        public String title;
        public int  albumId;
        public int  playsCounts;

    }
    public class SpecialColumn{
        public List<Column> list;
    }
    public class Column{
        public String coverPath;
        public String title;
        public String subtitle;
        public int specialId;

    }

}
