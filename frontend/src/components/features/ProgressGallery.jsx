import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCamera } from "@fortawesome/free-solid-svg-icons";
import PageTitle from "../ui/PageTitle";
import ProgressPictureCard from "../cards/ProgressPictureCard";
import { getProgressPictures, uploadProgressPicture } from "../../services/api";

import { SERVER_URL } from "../../constants";

export default function ProgressGallery({ collectionPlantId }) {
    const [pictures, setPictures] = useState([]);
    const [selectedImage, setSelectedImage] = useState("");
    const today = new Date().toLocaleDateString("en-US");
    const navigate = useNavigate();

    useEffect(() => {
        async function loadProgressPictures() {
            try {
                const data = await getProgressPictures(collectionPlantId);
                const filtered = data.filter((pic) => pic.collectionPlant.id === collectionPlantId);
                setPictures(filtered);
            } catch (error) {
                console.error("Failed to load progress pictures:", error);
            }
        }

        loadProgressPictures();
    }, [collectionPlantId]);

    function formatDate(date) {
        return new Date(date + "T00:00:00").toLocaleDateString("en-US", {
            month: "numeric",
            day: "numeric",
            year: "numeric",
        });
    }

    async function handleUpload(event) {
        const file = event.target.files?.[0];
        if (!file) return;

        // Instant preview while the upload happens.
        setSelectedImage(URL.createObjectURL(file));

        try {
            const newPicture = await uploadProgressPicture(collectionPlantId, file);
            // The backend returns the saved picture WITH its new id — add it to the gallery.
            setPictures((prev) => [...prev, newPicture]);
        } catch (error) {
            console.error("Failed to upload progress picture:", error);
        } finally {
            setSelectedImage(""); // clear the temporary preview
            event.target.value = ""; // reset input so the same file can be picked again
        }
    }

    return (
        <section id="progressGallery">
            <PageTitle title="Progress Pictures" />
            <div>
                <label htmlFor="progress-upload" className="file-upload-label">
                    <FontAwesomeIcon icon={faCamera} /> Add Progress Picture
                </label>
                <input
                    id="progress-upload"
                    type="file"
                    accept="image/*"
                    className="file-upload-input"
                    onChange={handleUpload}
                />
            </div>
            <div className="progress-pictures-cards-container">
                {pictures.map((progressPic) => (
                    <ProgressPictureCard
                        key={progressPic.id}
                        fileName={progressPic.imagePath}
                        date={formatDate(progressPic.pictureDate)}
                        src={`${SERVER_URL}/uploads/progress-pictures/${progressPic.imagePath}`}
                        onClick={() => navigate(`/progress-picture/${progressPic.id}`)}
                    />
                ))}
                {selectedImage && <ProgressPictureCard src={selectedImage} date={today} />}
            </div>
        </section>
    );
}
