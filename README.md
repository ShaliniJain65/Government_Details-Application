# Government_Details-Application
An application which gives all the details(emails, picture,social media) of the current government bodies. The details are displayed on the state you select.

The Project is an Android Application built in Android Studio.
The application is a multiple-activity application consisting of data fetching from the Google Civic Information API using REST services. To make the application run faster we are using AsyncTask.
The first Activity has and search bar for tying the state/city details and then it fetches the rest of the data from the JSON format to display as the recycler view Adapter.
The Second Activity has the INFO page which consists of the politician's information such as email, phone, address, party, social media links(youtube,twitter,linkedin.google) using Linky and the profile picture using Picasso Library.
The entire application follows the Android app life cycle. Also saves the instance of the app.
The Third Activity is about the image view of the politician which using loading image and not available image if the image of the politician is not present in JSON data fetched.
The Internet is mandatory for this application.
Have both landscape and Portrait Orientation designing.
