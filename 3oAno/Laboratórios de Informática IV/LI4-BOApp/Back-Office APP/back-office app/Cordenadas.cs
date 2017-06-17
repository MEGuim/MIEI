using System;

namespace Mobile_APP
{
    public class Cordenadas
    {
        public double latitude { get; set; }
        public double longitude { get; set; }

        public Cordenadas() {
            this.latitude = 0.0f;
            this.longitude = 0.0f;
        }

        public Cordenadas(double lgtd, double lat) {

            this.longitude = lgtd;
            this.latitude = lat;

        }

        public String toString() {

            return "Latitude: " + latitude + ", Longitude: " + longitude;
        }

   
    }
}