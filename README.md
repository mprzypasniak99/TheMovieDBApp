# The MovieDB App

This app allows user to fetch list of movies that are now playing in the cinemas, using TMDB API.
It also allows for marking movies as favourites. Info about favourites is stored on the device and
is available after app restart.

## Requirements
To build the project, token for TMDB API must be added to the project.

1. Create ``apikeys.properties`` file in the root folder of the project
2. Add the token under the key ``TOKEN``
3. Only token is needed, no need to add a ``Bearer`` prefix