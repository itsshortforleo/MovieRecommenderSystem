-- create schema movie_recommender;

CREATE TABLE `movie_recommender`.`movies` (
  `movieID` INT NOT NULL,
  `title` VARCHAR(200) NULL,
  `imbdID` INT NULL,
  `spanishTitle` VARCHAR(200) NULL,
  `imdbPictureURL` VARCHAR(300) NULL,
  `year` INT NULL,
  `rtID` VARCHAR(100) NULL,
  `rtAllCriticsRating` INT NULL,
  `rtAllCriticsNumReviews` INT NULL,
  `rtAllCriticsNumFresh` INT NULL,
  `rtAllCriticsNumRotten` INT NULL,
  `rtAllCriticsScore` INT NULL,
  `rtTopCriticsRating` DECIMAL(2,1) NULL,
  `rtTopCriticsNumReviews` INT NULL,
  `rtTopCriticsNumFresh` INT NULL,
  `rtTopCriticsNumRotten` INT NULL,
  `rtTopCriticsScore` INT NULL,
  `rtAudienceRating` DECIMAL(2,1) NULL,
  `rtAudienceNumRatings` INT NULL,
  `rtAudienceScore` INT NULL,
  `rtPictureURL` VARCHAR(200) NULL,
  PRIMARY KEY (`movieID`));

CREATE TABLE `movie_recommender`.`movie_genres` (
  `genre` VARCHAR(100) NOT NULL,
  `movieID` INT NOT NULL,
  PRIMARY KEY (`genre`,`movieID`),
  CONSTRAINT fk_movie_genres_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));

CREATE TABLE `movie_recommender`.`movie_countries` (
  `country` VARCHAR(100) NOT NULL,
  `movieID` INT NOT NULL,
  PRIMARY KEY (`country`,`movieID`),
  CONSTRAINT fk_movie_countries_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));
  
CREATE TABLE `movie_recommender`.`movie_actors` (
  `actorID` VARCHAR(100) NOT NULL,
  `movieID` INT,
  `actorName` VARCHAR(200),
  `ranking` INT,
  PRIMARY KEY (`actorID`),
  CONSTRAINT fk_movie_actors_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));
  
CREATE TABLE `movie_recommender`.`movie_directors` (
  `directorID` VARCHAR(100) NOT NULL,
  `movieID` INT,
  `actorName` VARCHAR(200),
  PRIMARY KEY (`directorID`),
  CONSTRAINT fk_movie_directors_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));  
  
CREATE TABLE `movie_recommender`.`tags` (
  `tagID` INT NOT NULL,
  `value` VARCHAR(200),
  PRIMARY KEY (`tagID`));

CREATE TABLE `movie_recommender`.`user_tagged_movies` (
  `userID` INT NOT NULL,
  `movieID` INT NOT NULL,
  `tagID` INT NOT NULL,
  `date_day` INT,
  `date_month` INT,
  `date_year` INT,
  `date_hour` INT,
  `date_minute` INT,
  `date_second` INT,
  PRIMARY KEY (`userID`, `movieID`, `tagID`),
  CONSTRAINT fk_user_tagged_movies_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID), 
  CONSTRAINT fk_user_tagged_movies_tagID FOREIGN KEY(tagID) REFERENCES tags(tagID));  
  
CREATE TABLE `movie_recommender`.`user_tagged_movies_timestamps` (
  `userID` INT NOT NULL,
  `movieID` INT NOT NULL,
  `tagID` INT NOT NULL,
  `timestamp` INT,
  PRIMARY KEY (`userID`, `movieID`, `tagID`),
  CONSTRAINT fk_user_tagged_movies_timestamps_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID), 
  CONSTRAINT fk_user_tagged_movies_timestamps_tagID FOREIGN KEY(tagID) REFERENCES tags(tagID));

CREATE TABLE `movie_recommender`.`movie_tags` (
  `movieID` INT NOT NULL,
  `tagID` INT NOT NULL,
  `tagWeight` INT,
  PRIMARY KEY (`movieID`, `tagID`),
  CONSTRAINT fk_movie_tags_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID), 
  CONSTRAINT fk_movie_tags_tagID FOREIGN KEY(tagID) REFERENCES tags(tagID)); 

CREATE TABLE `movie_recommender`.`user_rated_movies` (
  `userID` INT NOT NULL,
  `movieID` INT NOT NULL,
  `rating` DECIMAL(2,1) NULL,
  `date_day` INT,
  `date_month` INT,
  `date_year` INT,
  `date_hour` INT,
  `date_minute` INT,
  `date_second` INT,
  PRIMARY KEY (`userID`, `movieID`),
  CONSTRAINT fk_user_rated_movies_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));
  
CREATE TABLE `movie_recommender`.`user_rated_movies_timestamps` (
  `userID` INT NOT NULL,
  `movieID` INT NOT NULL,
  `rating` DECIMAL(2,1) NULL,
  `timestamp` INT,
  PRIMARY KEY (`userID`, `movieID`),
  CONSTRAINT fk_user_rated_movies_timestamps_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));

















