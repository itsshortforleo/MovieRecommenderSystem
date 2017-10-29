create schema movie_recommender;

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
  CONSTRAINT fk_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));

CREATE TABLE `movie_recommender`.`movie_genres` (
  `genre` VARCHAR(100) NOT NULL,
  `movieID` INT NOT NULL,
  PRIMARY KEY (`genre`,`movieID`),
  CONSTRAINT fk_movieID FOREIGN KEY(movieID) REFERENCES movies(movieID));