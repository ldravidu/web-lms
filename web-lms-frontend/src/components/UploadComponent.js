import React, { useState } from "react";
import axios from "axios";

const UploadComponent = () => {
  const [file, setFile] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [progress, setProgress] = useState(0);
  const [error, setError] = useState(null);

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      setFile(selectedFile);
      setError(null);
    }
  };

  const uploadFile = async () => {
    if (!file) {
      setError("Please select a file");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);
    formData.append("courseId", "default-course"); // TODO: Replace with actual course ID

    setUploading(true);
    setError(null);

    try {
      const response = await axios.post("/api/files/upload", formData, {
        onUploadProgress: (progressEvent) => {
          const percentCompleted = Math.round(
            (progressEvent.loaded * 100) / progressEvent.total,
          );
          setProgress(percentCompleted);
        },
      });

      setUploading(false);
      setProgress(0);
      setFile(null);
      alert("File uploaded successfully");
    } catch (error) {
      setUploading(false);
      setError(error.response?.data?.message || "Upload failed");
    }
  };

  return (
    <div className="upload-container">
      <h2>Upload Course Content</h2>
      <div className="upload-form">
        <input
          type="file"
          onChange={handleFileChange}
          disabled={uploading}
          accept=".pdf,.jpg,.jpeg,.png,.mp4"
        />
        {file && (
          <div className="file-info">
            <p>Selected: {file.name}</p>
            <p>Size: {(file.size / 1024 / 1024).toFixed(2)} MB</p>
          </div>
        )}
        {error && <div className="error-message">{error}</div>}
        <button
          onClick={uploadFile}
          disabled={uploading || !file}
          className="upload-button"
        >
          {uploading ? `Uploading... ${progress}%` : "Upload File"}
        </button>
      </div>
    </div>
  );
};

export default UploadComponent;
