# Movie Watching Platform - PooTV

#### Made by: [Gabriela Limberea - 322CA](https://github.com/Gabi-Limberea)

## __Description__:

__PooTV__ is a movie watching platform that tries to achieve the same user
experience as Netflix. It allows the users to create an account, log in and
purchase movies from a given list of available movies, assuming that those
movies are not banned in their country. After purchasing a movie, the user can
watch it as many times as they want, they can like it, and they can give the
movie a rating.

## __App Flow__:

* _Added in stage 1_:
	* A __session__ starts on the unauthorized homepage, where there is no user
	  logged in. The user can then choose to log in or to create an account in
	  order to actually access the platform.

	* Upon logging in/registering, the user ends up on the authorized homepage.
	  From there, the user can access the movies page, the upgrades page or to
	  simply log out.

	* The movies page contains a list of movies available on the platform that
	  are also not banned in the user's country. The user can search for movies
	  that start with a given string, or they can filter the movies by
	  containing genres and actors, and to sort the results by movie duration
	  and/or rating.

	* From the movies page, the user can choose a specific movie to focus on.
	  There, they can purchase the movie, watch it, like it and rate it.

	* The upgrades page allows the user to upgrade to a premium account and to
	  buy tokens used for purchasing movies.

	* The logout page is accessible from all pages after the user has logged in.
	  It allows the user to log out and to return to the unauthorized homepage.
	  From there, another user can log in or register.
* _Added in stage 2_:
	* The movie database can now be modified, in that movies can be added or
	  removed from it.
	* A user can now subscribe to a genre and receive notifications when a
	  movie from that genre (given that it is available in their country) is
	  added or removed from the database.
	* Premium users now receive recommendations at the end of the input cycle.
	* The user can now go to the previous page they were on, by pressing the
	  back button. (I know, very innovative, right?)

## __Flow Chart__:

```mermaid
	
	flowchart LR
			UH(Unauthorized Homepage) --> L(Log In)
			UH --> R(Register)
			L --Wrong credentials---> UH
			R --User already exists---> UH
			L --Correct credentials---> AH(Authorized Homepage)
			R --User created---> AH
			AH <--> M(Movies)
			AH <--> U(Upgrades)
			AH --> LO(Log Out)
			U --> LO
			M --> LO
			M <--> MD(See Movie Details)
			U <--> M
			MD --> LO
			MD --> AH
			MD --> U
			U --> U
			MD --> MD
			M --> M
			LO --> UH
	
```

## __Design and Implementation__:

### ___Stage 1___:

The platform was implemented using 4 major classes:

* __Session__:
	* loads the session data: users and list of movies
	* manages the execution of the users' actions and the output flow of the
	  platform
	* __Movie__:
		* stores the movie data: title, year of release, duration, overall
		  rating, actors, genres, etc.
		* can be updated as the users interacts with it (rating, likes)
	* __User__:
		* stores the user's data: credentials (name, password, account type,
		  country), purchased movies, liked movies, rated movies.
		* manages the user's actions: buy movie, watch movie, like movie, rate
		  movie
	* __Page__:
		* is the base for all the pages on the platform
		* is abstract
		* stores the page's name

In order to model each page type and its behavior, the ___Page___ class had
to be extended, thus resulting the following hierarchy:

```mermaid
	graph TD
	
	subgraph  Class Type / Design Pattern
		AbstractClass((Abstract))
		Concrete
		Singleton[(Singleton)]
	end
	
	P((Page)) --> UH[(Unauthorized Homepage)]
	P --> AH[(Authorized Homepage)]
	P --> L[(Log In)]
	P --> R[(Register)]
	P --> M(Movies)
	P --> MD(See Movie Details)
	P --> U(Upgrades);
	
```

The logout page is not a concrete page/class. Whenever a user logs out, they
are removed as the current user and the session goes back to the
unauthorized homepage.

In the concrete classes, there are only implemented the Page type specifics
(e.g. for the classes that support actions, each implements its own actions
and that's it). For the page object generation, a Page factory is
used. In order to model the pages that supported actions, a Strategy
design pattern was used. There are also some helper classes and enums.

### ___Stage 2___:

In order to add the subscription system, there is now a __Subscription
Manager__ included in the __Session__. The __Subscription Manager__
keeps track of the list of subscribers for each genre, thus becoming the
middle man between the movie database and the users. Using an Observer
design pattern, the __Subscription Manager__ supervises the movie database and
notifies the users when a movie from a genre they are subscribed to is added
or removed. There is also a __Recommendation Manager__ that is used to send the
recommendations to the premium users, at the end of the input cycle.
