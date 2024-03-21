import React from 'react';
import './Profile.css';
import cookie from 'react-cookies';
import '@fortawesome/fontawesome-free/css/all.min.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';
import {
    // MDBBox,
    MDBRow, MDBCol, MDBCard, MDBCardImage, MDBCardTitle, MDBCardBody, MDBCardText, MDBIcon, MDBContainer, MDBCardFooter
} from 'mdbreact';

class Profile extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            username: '',
            email:'',
            asked: 0,
            answered: 0,
            DOB:'',
            mentor_role:0,
            user_id:"anonymous",
    
        }
    }
    

    componentDidMount() {
        var usernam = cookie.load('username');
        var userid = cookie.load('user_id');
        var mentor_rol = cookie.load('mentor_role');
        if (usernam === undefined) {
            window.location.href = '/signin';
        } else {
            this.setState({username: usernam});
        }
        if (mentor_rol === undefined || mentor_rol === false) {
            this.setState({mentor_role: 0})
        } else {
            this.setState({ mentor_role: 1});
        }

        let self = this;

		const chunks = [];
		var result = " not updated";
		var base_url = cookie.load('base_url');

		fetch(`${base_url}/profile`, {
			method: 'POST',
			headers: {
				
			},
			body: JSON.stringify({"user_id":userid})
		}).then(function (response) {

			const reader = response.body.getReader();

			reader.read().then(({ value, done }) => {
				var string = new TextDecoder("utf-8").decode(value);
				chunks.push(string);
			}).then(() => {
				console.log(chunks[0]);
                result = JSON.parse(chunks[0]);
                console.log("results:"+JSON.stringify(result))
			}).then(() => {
                self.setState({username:result[0].username });
                self.setState({email:result[0].email});
                self.setState({DOB:result[0].dob});
                self.setState({user_id:userid});
                self.setState({asked:result[0].q_count});
                self.setState({answered:result[0].ans_count});
				});
				console.log(self.state);
			});
		
    }


    render() {
        return (
            <MDBContainer>
                <MDBRow middle>
                    <MDBCol middle style={{maxWidth: "40rem", marginLeft: "25%", marginTop: "3%", marginBottom: '3%'}}>
                        <MDBCard reverse>
                            <MDBCardImage cascade style={{height: '20rem'}}
                                          src="https://mdbootstrap.com/img/Photos/Slides/img%20(70).jpg"/>
                            <MDBCardBody cascade className="text-center">
                                <MDBCardTitle>{this.state.username}</MDBCardTitle>
                                <MDBCardText className="text-center">{(this.state.mentor_role === 1) ?
                                    <h4>Moderator</h4> : <h4>User</h4>}</MDBCardText>
                                <h5 className="indigo-text"><strong>{this.state.user_id}</strong></h5>
                                <MDBCardText>{this.state.email}<br/>{this.state.DOB}</MDBCardText>
                                <MDBIcon icon="star"/><MDBIcon icon="star"/><MDBIcon icon="star"/><MDBIcon icon="star"/><MDBIcon
                                icon="star"/><br/>
                                <a href="#!" className="icons-sm li-ic ml-1">
                                    <MDBIcon fab icon="linkedin-in"/></a>
                                <a href="#!" className="icons-sm tw-ic ml-1">
                                    <MDBIcon fab icon="twitter"/></a>
                                <a href="#!" className="icons-sm fb-ic ml-1">
                                    <MDBIcon fab icon="facebook-f"/></a>
                            </MDBCardBody>
                            <MDBCardFooter style={{textAlign: "center"}}>No.of Questions asked/answered:</MDBCardFooter>
                            <MDBCardText
                                style={{textAlign: "center"}}>{this.state.asked}/{this.state.answered}</MDBCardText>
                        </MDBCard>
                    </MDBCol>
                </MDBRow>
            </MDBContainer>

        );
    }
}

export default Profile;
