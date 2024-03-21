//===========================================
// IMPORT DEPENDENCIES
//===========================================
import React, {Component} from "react";
import Slideshow from "./Slideshow";
import slide1 from "./assets/slide1.jpg";
import slide2 from "./assets/slide2.jpg";
import slide3 from "./assets/slide3.jpg";
import "./homepage.css";


//===========================================
// CREATE STYLES OBJECT
//===========================================
const s = {
    container: "screenH dGray col",
    header: "flex1 fCenter fSize2",
    main: "flex8 white",
    footer: "flex1 fCenter"
};

//===========================================
// SLIDES DATA
//===========================================
const slides = [slide1, slide2, slide3];

//===========================================
// APP COMPONENT
//===========================================
class HomePage extends Component {
    render() {
        return (
            <div className="HomePage">

                <div className={s.container}>

                    <div className={s.main}>
                        <Slideshow slides={slides}/>
                    </div>

                </div>

                <div className="Iam">

                    <p>Unable to</p>
                    <b>
                        <div className="innerIam">
                            Fix the code<br/>
                            Complete your code<br/>
                            Find the optmisized version<br/>
                            Find the right answer
                        </div>
                    </b>
                </div>
                <div className="fun-facts">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-6">
                                <div className="left-content">
                                    <h2>Find solutions for your <em>problems here.</em></h2>
                                    <div class="area">⚠ Bug Fixing ⚠</div>

                                    <p style={{fontSize: '20px'}}>This is an user friendy platform where user meet and
                                        exchange views about the problems that they face while developing or writing a
                                        code.
                                        <br/>To know more about this platform please signup and explore it.</p>

                                </div>
                            </div>
                            <div className="col-md-6 align-self-center">
                                <div className="row">
                                    <div className="col-md-6">
                                        <div className="count-area-content">
                                            <div className="count-digit">945</div>
                                            <div className="count-title">No of users visited</div>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="count-area-content">
                                            <div className="count-digit">1280</div>
                                            <div className="count-title">No of questions</div>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="count-area-content">
                                            <div className="count-digit">578</div>
                                            <div className="count-title">Questions answered</div>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="count-area-content">
                                            <div className="count-digit">26</div>
                                            <div className="count-title">Users registered</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <ul className="social-icons">
                    <li><a href="https://www.instagram.com/livingindiatv/" style={{height: '50px', width: '50px'}}>instagram<span></span></a>
                    </li>
                    <li><a href="https://in.pinterest.com/livingindiatv/" style={{height: '50px', width: '50px'}}>pinterest<span></span></a>
                    </li>
                    <li><a href="https://youtube.com" style={{height: '50px', width: '50px'}}>youtube<span></span></a>
                    </li>
                    <li><a href="https://plus.google.com/102689304172526656580" style={{height: '50px', width: '50px'}}>gplus<span></span></a>
                    </li>
                    <li><a href="https://twitter.com/livingindiatv"
                           style={{height: '50px', width: '50px'}}>twitter<span></span></a></li>
                    <li><a href="https://www.facebook.com/livingindiatv/" style={{height: '50px', width: '50px'}}>facebook<span></span></a>
                    </li>
                </ul>

                <a href="/feedback">
                    <button type="button" className="btn btn-secondary feedbackbtn">
                        Give Feedback
                    </button>
                </a>

            </div>
        );
    }
}

export default HomePage;
