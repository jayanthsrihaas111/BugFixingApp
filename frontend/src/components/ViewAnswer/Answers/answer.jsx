import React, { Component } from 'react';
import cookie from 'react-cookies';

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

class ViewerAnswer extends Component {
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
		console.log("This is viewer answer, damnit "+this.props.accp);
		let self = this;
		self.setState({answer_id:self.props.ans_id});
		self.setState({accp:self.props.accp});
		self.setState({likes_count:self.props.likes});
		self.setState({full_answer:self.props.full_answer});
		self.setState({user_id:self.props.userid});
	}

	handleLike = (e) => {
		e.preventDefault();

		if(this.state.answer_id=== undefined) {
            window.location.href = '/dashboard';
		}
		else if(this.state.liked === true){
				//do nothing
				alert("You have already liked the answer!");
				
		}
		else {
			

            //Code for fetching answer

            let self = this;
            
            const chunks = [];
            var result = " not updated";
            var base_url = cookie.load('base_url');
    
            fetch(`${base_url}/update_like`, {
                method: 'POST',
                headers: {
                    
                    
                },
                body: JSON.stringify({"Ans_id":this.state.answer_id,"likes_prev":this.state.like_count})
            }).then(function (response) {
    
                const reader = response.body.getReader();
    
                reader.read().then(({ value, done }) => {
                    var string = new TextDecoder("utf-8").decode(value);
                    //console.log(string);
                    chunks.push(string);
                }).then(() => {
                    console.log("chunks:" + chunks);
                    if(chunks.length > 0){
                        result = JSON.parse(chunks);
                        console.log("result:" + JSON.stringify(result[0]));
                    }
                }).then(() => {
                    self.setState({ answers: result });
                    
                    console.log(self.state.answers);
					console.log(self.state.answers[0].likes_new);
					self.setState({like_count:self.state.answers[0].likes_new});
					self.setState({liked:true});
                }
                );
            })


        }

	}
	// handleChangeLikes = (e) = {
	// 	this.setState
	// }

	render() { 
		var colors = ['purple-gradient', 'aqua-gradient', 'peach-gradient', 'blue-gradient'];

		return (
			<div>
			{ this.props.accp === "1"?
			<div className="card" style={{width: "90%"}}>
                                        <div className="card-header">
                                            {this.props.userid}
                                        </div>
                                        <div className="card-body">

                                            <p className="card-text" style={{color:"black"}}>
								  <div dangerouslySetInnerHTML={{ __html: this.props.full_answer }} />
								  </p>
                                        </div>
                                        <div className="card-footer text-right">
                                            <MDBIcon icon="thumbs-up" className="m-3" onClick={this.handleLike}  />{this.state.like_count}
                                            <div className="d-inline" style={{backgroundColor:"green",color:"white"}}>Accepted</div>
                                        </div>
                                    </div>: <p></p>
		}
		</div>
		)
	}
}

export default ViewerAnswer;