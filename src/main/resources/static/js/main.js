
//MODELS

//create a model called Song (extends the base Mode provided by Backbone)
// by convention begin the Model variable name with Capital letter
// and the instance variable with same name as with lower letter


	var Song = Backbone.Model.extend({
		initialize: function(){
			console.log("A new song has been created.")
		},
		defaults:{
			genere:'jazz'
		},
		validate: function(attributes){
			if(!attributes.type){
				return "type attribute is required."
			}
		},
		play: function(){
			console.log('playing song..')
		}
	})


//instantiate the model Song
	var song = new Song({
		'type':'classical',
		'length':123
	});
	song.set("name","song1");


//various attributes of the song instance.
	console.log(song)
	console.log(song.attributes)
	console.log(song.toJSON())
	console.log(song.get('type'))
	song.unset('length')
	console.log(song.has('length'))
	console.log(song.has('type'))
	console.log(song.attributes)
	song.clear();
	console.log(song.attributes)
	console.log(song.isValid())
	console.log(song.validationError)
	song.set('type','122')
	console.log(song.attributes)
	console.log(song.isValid())
	console.log(song.validationError)

//method call using instance 
	song.play();

//inheritance
	var SubSong = Song.extend();
	subSong = new SubSong();
	subSong.play();

//server call

	var Course = Backbone.Model.extend({
		urlRoot: "/api/courses"
	});

	//1. Create a course
	var course2 = new Course();
    course2.set("title","python");
    course2.set("content","python desc");
    //var save_res = course2.save();
    //console.log('Saved successfully :',save_res.responseJSON);

    //2. Get All courses from mysql
    var course = new Course();
	var result = course.fetch({
	    success: function(){
	        return console.log('fetched successfully'+result);
	    },
	    error: function(){
	        return console.log('fetch failed');
	    }
	});

//	//3. Modify a course
//	var course3 = new Course({id:4});
//    course3.set("title","angular js");
//    course3.set("content","angular js desc");
//    course3.save({},{
//        success: function(){
//            return console.log('saved successfully');
//        },
//        error: function(){
//            return console.log('saved failed');
//        }
//    });
//
//	//4. Delete a course (delete the lastly created course)
//	//console.log('Deleting course id :'+save_res.responseJSON.id);
//
//    //code before the pause
//    setTimeout(function(){
//        //do what you need here
//        console.log('Delete course id :',save_res.responseJSON.id);
//
//        var course4 = new Course({id:save_res.responseJSON.id});
//            course4.destroy({
//                success: function(){
//                    return console.log('deleted successfully');
//                },
//                error: function(){
//                    return console.log('delete failed');
//                }
//            });
//    }, 2000);





//COLLECTIONS

    var Courses = Backbone.Collection.extend({
        url: "/api/courses",
        model: Course
    });

    var courses = new Courses([
        new Course({title:'abc'}),
        new Course({title:'def'})
    ]);

    courses.add(new Course({title:'ghi'})); // add an item to collection
    courses.add(new Course({title:'123'}),{at:0}); // add an item to collection at 0th index
    courses.push(new Course({title:'345'})); // add an item to collection

    courses // pring  courses Collections
    courses.at(0) // get 0th item from collections
    courses.get("c7") // get by id
    courses.remove(courses.at(3)) // remove an item from collection

    //find and findWhere
    var def = courses.where({title:'def'})
    var whereIsdef = courses.findWhere({title:'def'})
    console.log('find of def',def)
    console.log('where is def',whereIsdef)

   var allCourses = new Courses();
	var result = allCourses.fetch({
	    success: function(){
	        return console.log('fetched successfully'+result);
	    },
	    error: function(){
	        return console.log('fetch failed');
	    }
	});
	console.log('all courses',allCourses)
	console.log('all courses length:'+allCourses.length)


// VIEWS

    //view for model
    var CourseView = Backbone.View.extend({
        initialize:function(options){
            this.bus=options.bus;
        },
        events:{
            "click":"onClick",
        },

        onClick:function(){
            console.log('publish onclick event..');
            this.bus.trigger("courseSelected",this.model);
        },
        render: function() {

            // Note: by default all the html content are enclosed in a div.
            // create a new element and append the content to overcome this.
            var h6 = '<h6>'
            var h6_end = '</h6>'
            var tdStart = '<br/>'
            var title = '<b>title :</b>'
            var content = '<b>content :</b>'
            var createdAt = '<b>createdAt :</b>'
            var updatedAt = '<b>updatedAt :</b>'
            var courseContent=tdStart+this.model.id+
                                tdStart+title+this.model.get("title")+
                                tdStart+content+this.model.get("content")+
                                tdStart+createdAt+this.model.get("createdAt")+
                                tdStart+updatedAt+this.model.get("updatedAt");
            this.$el.html(courseContent);
            this.$el.attr("id",this.model.id);
            return this;
            //return courseContent;
        }

    });

    //view for collections
    var CoursesView = Backbone.View.extend({
        lastAddedCourseId:1,

        initialize: function(options){
            this.bus = options.bus;
            this.collection.on("add",this.onCourseAdded, this);
            this.collection.on("remove",this.onCourseRemoved, this);
        },
        onCourseAdded: function(crs){
            console.log('course added..');
            var courseView = new CourseView({model:crs, bus:this.bus});
            console.log(courseView.render().$el)
            this.$el.append(courseView.render().$el);
            this.$el.find("div#total").text("Course "+crs.attributes.title+" Added successfully");

            //this.render();

        },
        onCourseRemoved: function(course){
            console.log('course removed..');
            this.$el.find("div#"+course.id).remove();
            this.$el.find("div#total").text("Course "+course.id+":"+course.attributes.title+" Removed successfully");
        },
        events:{
            //"click .getButton": function(){this.onClickGetButton(this.collection)},
            "click .getButton": "onClickGetButton",
            "click .addButton":"onClickAddButton",
            "click .deleteButton":"onClickDeleteButton",
            "click .clearButton":"onClickClearButton"
        },
        onClickClearButton: function(e){
          console.log('clear ...');
          e.stopPropagation();
          this.$el.html('');
          this.$el.html(this.header());
          this.$el.find("div#total").remove();

        },
        onClickGetButton: function(courses){
            console.log('Get button on click event...'+courses.length);
            this.render();
        },
        onClickDeleteButton: function(e){
            e.stopPropagation();
            console.log('Delete button on click event...');
            var op=allCourses.fetch();
            var ID;
            setTimeout(function(){
                ID=op.responseJSON[0].id;
                console.log('ID: '+ID);
                var course4 = new Course({id:ID});
                course4.destroy();
            },1000);
        },
        onClickAddButton: function(e){
            e.stopPropagation();
            console.log('Add button on click event...');
            //1. Create a course
            var course2 = new Course();
            var cid=this.lastAddedCourseId++;
            console.log('increment id:'+cid);
            course2.set("title","python 2"+cid);
            course2.set("content","python 2 desc");
            allCourses.add(course2);
            var save_res = course2.save();
            setTimeout(function(){
                    console.log('Saved successfully :',save_res.responseJSON.id);
                    lastAddedCourseId=save_res.responseJSON.id;

            }, 1000);

        },
        header: function(){
            var buttonHtml='';
            var headerHtml = '<center>Backbone App using Spring boot</center>'
            var getButton = '<button class="getButton">Get All Course</button>'
            var addButton = '<button class="addButton">Add a Course</button>'
            var deleteButton = '<button class="deleteButton">Delete last Course</button>'
            var clearButton = '<button class="clearButton">Clear</button>'
            buttonHtml=headerHtml+'<br>'+getButton+addButton+deleteButton+clearButton+'<br/>';
            buttonHtml=buttonHtml+'<br> <div id="total">Total Courses: '+this.collection.length+"</div>";
            return buttonHtml;
        },
        render: function(){
            var self=this
            var getButton = '<button class="getButton">Get All Course</button>'
            var addButton = '<button class="addButton">Add a Course</button>'
            var deleteButton = '<button class="deleteButton">Delete last Course</button>'
            var clearButton = '<button class="clearButton">Clear</button>'
            var lastAddedCourseId;
            self.$el.html(this.header());
            self.collection.each(function(course){
                var courseView = new CourseView({model:course, bus: this.bus});
                self.$el.append(courseView.render().$el);
             }

            );
        }

    });

    var bus= _.extend({},Backbone.Events);
    //var coursesView = new CoursesView({ el:"#container", collection: allCourses});
    var coursesView = new CoursesView({ el:"#venues-container", collection: allCourses, bus: bus});

    setTimeout(function(){
        //do what you need here
            coursesView.render();
            console.log('all courses::::: inside timer',allCourses)
            console.log('all courses length::::: inside timer',allCourses.length)


    }, 20);

//=====================================

// EVENTS

    var person ={
        name:'xyz',
        walk: function(){
            this.trigger("walking_event",{speed:1,time:"8:00"});
        }
    };

    _.extend(person,Backbone.Events);

    person.on("walking_event",function(e){
           console.log('Person is walking.',e)
    });

    //person.once(..) // listen to event only once
    //person.off("walking_event");  // turn off the listener

    person.walk();


    var EventView = Backbone.View.extend({
        el:"#map-container",
        initialize: function(options){
            this.bus = options.bus;
            this.bus.on("courseSelected",this.onCourseSelected, this);
        },

        onCourseSelected: function(course){
            console.log('received onclick event..');
            this.model=course;
            this.render();
        },

        render: function(){
            if(this.model)
                this.$("#venue-name").html('Selected Course: '+this.model.get("id")+' - '+this.model.get("title"));
            return this;
        }
    });



    var eventView = new EventView({bus:bus});
    eventView.render();