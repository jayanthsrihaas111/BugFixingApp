import React, { Component } from 'react';
import cookie from 'react-cookies';

import {
	// MDBBtn,
	MDBCard,
	// MDBCardBody,
	MDBCardImage,
	// MDBCardTitle,
	// MDBCardText,
	MDBRow,
	MDBCol,
	// MDBIcon
} from 'mdbreact';

class Question extends Component {

	handleButtonClick = () => {
		cookie.save('Ques_id', this.props.Ques_id);
		console.log(this.props.Ques_id);
	}

	render() { 
		var colors = ['purple-gradient', 'aqua-gradient', 'peach-gradient', 'blue-gradient'];

		return (
			<MDBRow className="mycard">
				<MDBCol>
					<MDBCard>
						<MDBCardImage
							className={colors[this.props.flag]}
							tag='div'>
							<a href="/viewAnswer" onClick={this.handleButtonClick}><h5 className="question">{this.props.Question_Title}</h5></a>
						</MDBCardImage>
						<div className="body">
							<span className="details">
								<span class="details_item text-center">
									<h6>{this.props.Votes}</h6>
									<h6>Votes</h6>
								</span>
								<span class="details_item">
									<h6>{this.props.Answer_count}</h6>
									<h6>Answers</h6>
								</span>
								<span class="details_item">
									<h6>{this.props.Viewcount}</h6>
									<h6>Views</h6>
								</span>
							</span>
							<span className="tags">
								<span className="tagtag">Tags: </span>

								{
									this.props.Tags == null ?
										<h1>Component loading wait</h1> :
										this.props.Tags.map(tag => {
											return <span className="tag">{tag}</span>
										})
								}
								{/* <span className="tag">{this.props.Creator_id}</span> */}
								{/* <span className="tag">{this.props.Tags}</span>
								<span className="tag">webdev</span>
								<span className="tag">react</span>
								<span className="tag">frontend</span> */}
							</span>
						</div>
					</MDBCard>
				</MDBCol>
			</MDBRow>
		)
	}
}

export default Question;