import axios from "axios";
import React, { useState } from "react";

interface UploadContentProps {
  onUploadSuccess?: (message: string) => void;
}

const UploadContent = ({ onUploadSuccess }: UploadContentProps) => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [uploadProgress, setUploadProgress] = useState<number>(0);
  const [error, setError] = useState<string>("");
  const [courseId, setCourseId] = useState<string>("");

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];

    if (file) {
      setSelectedFile(file);
      setError("");
    }
  };

  const uploadFile = async () => {
    if (!selectedFile || !courseId) {
      setError("Please select a file and enter a course ID");
      return;
    }

    console.log("Starting upload...");
    console.log("Selected file:", selectedFile);
    console.log("Course ID:", courseId);

    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("courseId", courseId);

    console.log("FormData:", Object.fromEntries(formData));

    try {
      const response = await axios.post("/api/content/upload", formData, {
        onUploadProgress: (progressEvent) => {
          if (progressEvent.total !== undefined) {
            const progress = Math.round(
              (progressEvent.loaded / progressEvent.total) * 100,
            );
            setUploadProgress(progress);
          }
        },
      });

      console.log("Upload response:", response.data);

      setError("");
      setSelectedFile(null);
      setUploadProgress(0);

      onUploadSuccess?.(`File "${selectedFile.name}" uploaded successfully`);
    } catch (error: any) {
      console.error("Upload error:", error);
      setError(error.response?.data || "Failed to upload file");
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-6">Upload Course Content</h2>

      <div className="space-y-4">
        <div className="space-y-2">
          <label className="text-sm font-medium">Course ID</label>
          <input
            type="text"
            value={courseId}
            onChange={(e) => setCourseId(e.target.value)}
            className="w-full p-2 border rounded-md"
            placeholder="Enter course ID (e.g., CS101)"
          />
        </div>

        <div className="space-y-2">
          <label className="text-sm font-medium">Select File</label>
          <input
            type="file"
            onChange={handleFileChange}
            accept=".pdf,.mp4,.jpg,.jpeg,.png"
            className="w-full"
          />
        </div>

        {selectedFile && (
          <div className="space-y-4">
            <button
              onClick={uploadFile}
              className="w-full bg-primary text-white py-2 px-4 rounded-md hover:bg-primary/90 transition-colors"
            >
              Upload File
            </button>

            <div className="w-full bg-secondary rounded-full h-2.5">
              <div
                className="bg-primary h-2.5 rounded-full transition-all duration-500"
                style={{ width: `${uploadProgress}%` }}
              />
            </div>

            <span className="text-sm text-muted-foreground">
              {uploadProgress}% Complete
            </span>
          </div>
        )}

        {error && (
          <div className="p-4 bg-red-100 text-red-700 rounded-md">{error}</div>
        )}
      </div>
    </div>
  );
};

export default UploadContent;
