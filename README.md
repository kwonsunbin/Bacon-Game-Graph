# Six Degrees of Kevin Bacon
SNU CS Data Structure 2020 - Project3

Problem Statement
“Six Degrees of Kevin Bacon is a trivia game based on the concept of the small world phenomenon and rests
on the assumption that any individual involved in the Hollywood, California film industry can be linked through
his or her film roles to actor Kevin Bacon within six steps.”1 The object of this game is to link a movie actor to
Kevin Bacon via shared movie roles (called collaboration links). The Bacon number of an actor is the degrees of
separation he or she has from Kevin Bacon. For example, Tom Hanks has a Bacon number of one, as he was in
Apollo 13 with Kevin Bacon. Sally Field has a Bacon number of two, because she was in Forrest Gump
with Tom Hanks. This game is in fact a graph problem and a simple form of social network analysis.2
For this programming assignment, we design a Java program that allows us to explore complex co-star relationships
among actors and carry out a variety of analyses on the Internet Movie Database.3 For example, we
may be interested in finding out how influential an actor is in the movie industry (or, in general, how influential
a person is within a social network). The more influential (or central) an actor is, the lower his/her total or maximum
distance to all the other actors is. As another example, a person is often said to be between two people if the
person is on a shortest path between them. The betweenness centrality is a measure of the control a person has on
the communication between others. In order to make these analyses feasible, we need be able to compute some
critical measures such as the shortest distance and the number of distinct shortest paths for any pair of people in a
social network.
