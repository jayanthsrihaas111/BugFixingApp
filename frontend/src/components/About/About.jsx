import React from 'react';
import './About.scss';

function About() {
    return (


        <div className="Bela">

            <div className="xyz">
                <div className="yyy">
                    <h1>About </h1>
                    <p className="txt">
                        A platform to help your developers fix bugs faster. Our site Bug Fixing Assistance Portal aims
                        to connect users and technical support in order to update users on bugs that need to be
                        addressed and solved. It is a powerful portal that enables users to report new bugs and the
                        moderators to solve or suggest fixes.
                    </p>
                    <h2> Home Page</h2>
                    <p className="txt">
                        Some person want to use this portal as they find this portal exciting and want to get to know
                        more about the technical related aspects then immediately the person should register by giving
                        their respective details as asked in homepage .By Registering the person can become a "User" for
                        the portal specified with their mentioned name as their Id. By regestering for the portal the
                        user can be directed to login page.
                    </p>
                    <h2> How To Use The Portal</h2>
                    <p className="txt">
                        To post or answer for technical support about the bug issues the user need to login so that it
                        could be directed to next page i.e,User’s Dashboard after successful login where all questions
                        asked in the fourm are displayed.Questions will be hovered in bold when you scroll the
                        keypointer on it . You can click to view the answers
                    </p>
                    <h2> Ask Question</h2>
                    <p className="txt"> If the User want to post a question he can click on the ask question where user
                        can add the question so that every user can see the question posted.</p>
                    <h2> Feedback and Votes</h2>
                    <p className="txt">
                        For each Answer posted for the question a User can vote if he finds the answer or post helpful.
                        Max Votes given for an answer is given highest priority for that question. We can see views, no
                        of votes and number of answers for a paticular question in the card presented in Dashboard.
                        Feedback is very important from the Users so that developers of the site will get to know about
                        the user experience and if there is any discripancy the developers of this site will get to know
                        and could make it better.
                    </p>
                    <div className="skl">
                        <span>Web Design</span>

                        <span>Coding</span>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default About;
