import React, { Component } from 'react';
import cookie from 'react-cookies';
import ViewAnswer from './answer.jsx';
import MentorAnswer from './mentorAnswer.jsx';
import {
	 MDBBtn,
	MDBCard,
	 MDBCardBody,
	MDBCardImage,
	MDBCardTitle,
	MDBCardText,
	MDBRow,
	MDBCol,
	MDBIcon
} from 'mdbreact';
import ViewerAnswer from './answer.jsx';

class RenderAnswer extends Component {
	constructor(props){
		super(props);
		this.state  = {
			answer_id: "",
			accp: "",
			full_answer: "",
			user_id:"",
			liked:false,
			like_count: this.props.likes,
		}
	}
	componentDidMount() {
		console.log("This is render answer, damnit "+this.props.role+" "+typeof(this.props.role));
		let self = this;
		self.setState({answer_id:self.props.ans.ans_id});
		self.setState({accp:self.props.ans.accp});
		self.setState({likes_count:self.props.ans.likes});
		self.setState({full_answer:self.props.ans.full_answer});
		self.setState({user_id:self.props.ans.userid});
	}

	
	// handleChangeLikes = (e) = {
	// 	this.setState
	// }

	render() { 
		var colors = ['purple-gradient', 'aqua-gradient', 'peach-gradient', 'blue-gradient'];
        const role_r = this.props.role;
		return (
			<div>
            {role_r==="true"?
            <MentorAnswer ans_id={this.props.ans.answer_id} userid={this.props.ans.user_id} full_answer={this.props.ans.full_answer} likes={this.props.ans.like_count} accp={this.props.ans.accp} />
            :
            <ViewerAnswer ans_id={this.props.ans.answer_id} userid={this.props.ans.user_id} full_answer={this.props.ans.full_answer} likes={this.props.ans.like_count} accp={this.props.ans.accp} />
             }
		</div>
		)
	}
}

export default RenderAnswer;