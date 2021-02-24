/*
* @Author: dretrtg
* @Date:   2019-02-11 20:06:59
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-02-24 21:06:43
*/

'use strict';
import React from 'react';
import ReactDOM from 'react-dom';

const PageTitle = React.createClass({
    componentDidMount(){
        document.title = this.props.pageTitle || 'GrVolunteer Admin'
    },
    render() {
        return (
            <div className="row">
                <div className="col-lg-12">
                    <h1 className="page-header">{this.props.pageTitle}</h1>
                    {this.props.children}
                </div>
            </div>
        );
    }
});

export default PageTitle;