
var ArtistsView = Backbone.View.extend({
    render: function(){
        this.$el.html('Artists Page');
        return this;
    }
});

var AlbumsView = Backbone.View.extend({
    render: function(){
        this.$el.html('Albums Page');
        return this;
    }
});

var GenreView = Backbone.View.extend({
    render: function(){
        this.$el.html('Genre Page');
        return this;
    }
});

var NavView = Backbone.View.extend({
    events: {
        "click": "onClick"
    },
    onClick: function(e){
        var $li = $(e.target);
        appRouter.navigate($li.attr("data-url"),{trigger: true});
    },
    render: function(){

        return this;
    }
});

var navView = new NavView({el:"#nav"});

var AppRouter = Backbone.Router.extend({
    routes:{
        "albums":"viewAlbums",
        "albums/:albumId":"viewAlbumsById",
        "artists":"viewArtists",
        "genres":"viewGenres",
        "albums":"viewAlbums",
        "*other":"defaultRoute",
    },

    viewAlbumsById:function(albumId){
        console.log('view album by id '+albumId);
    },
    viewAlbums:function(){
        var view = new AlbumsView({el: "#container"});
        view.render();
    },
    viewArtists:function(){
        var view = new ArtistsView({el: "#container"});
        view.render();
    },
    viewGenres:function(){
        var view = new GenreView({el: "#container"});
        view.render();
    },
    defaultRoute:function(){
        console.log('invalid url');
    }
});

var appRouter = new AppRouter();
Backbone.history.start();