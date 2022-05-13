# Shopify Backend Development Intern Challenge - Fall 2022
*Bryan Meyer*

## Introduction

Dear Shopifolk,

Thank you for taking the time to look at my challenge code. I apologize in advance for the user interface :)

Some key points you might want to know with regards to certain design and other decisions:
- I chose Spring as my framework because Java is the language I know best. I haven't worked with Spring much so if anything seems "not very Spring", that's why. If you're interested, I've made a very similar inventory tracking app in Java EE [here](https://github.com/bryan0x17/CPRG352-final-project).
- I decided to make the backend REST-ish to keep it clean and decoupled while allowing for scalability and extensibility.
- I tried to build a React app for the frontend, but I don't know much about React and I felt it was overkill and not a good use of my time.
- I experimented with a Spring MVC controller as the backend but I decided to stick with the cleaner and simpler REST controller.

## Description

The following is my submission for the Shopify Backend Development Intern coding challenge. The goal is to make an inventory tracking web app for a logistics company.
The app has the basic CRUD functions, as well as the ability to delete items with a deletion comment, view a list of deleted items and their comments, and restore deleted items. The delete and restore functionality is achieved through a "soft delete" where the row is flagged using a boolean attribute that excludes it from normal searches.

The backend meets many of the requirements for a REST web service, with all endpoints accepting and returning JSON request and response bodies (where applicable).

## Technologies

This app is built using the Spring Boot Java framework. The app uses the built-in memory-based H2 database and Spring's JPA implementation for ORM and persistence/retrieval. The app serves static HTML pages and vanilla JS scripts.

## Deployment

The app uses Maven and can be run from the command line or from your chosen IDE. The app is also hosted on Replit and you can run it there by clicking the button. 

**Note**: Replit can be quite slow, so please be patient while the app builds and runs. Clicking "Generate Examples" is especially slow.

[![Run on Repl.it](https://repl.it/badge/github/bryan0x17/shopify_bed_2022)](https://repl.it/github/bryan0x17/shopify_bed_2022)