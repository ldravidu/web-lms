import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <h1>Course Content Manager</h1>
      </div>
      <div className="navbar-nav">
        <Link to="/" className="nav-link">
          Content List"
        </Link>
        <Link to="/upload" className="nav-link">
          Upload Content"
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
