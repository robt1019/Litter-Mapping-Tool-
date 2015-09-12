/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-07-05 at 20:54:28 UTC 
 * Modify at your own risk.
 */

package uk.ac.bristol.littermappingtool.backend.litterApi.model;

/**
 * Model definition for Litter.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the litterApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Litter extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String audioBlobRef;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String brand;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime created;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double latitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String litterType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double longitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String photoBlobRef;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userEmail;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAudioBlobRef() {
    return audioBlobRef;
  }

  /**
   * @param audioBlobRef audioBlobRef or {@code null} for none
   */
  public Litter setAudioBlobRef(java.lang.String audioBlobRef) {
    this.audioBlobRef = audioBlobRef;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBrand() {
    return brand;
  }

  /**
   * @param brand brand or {@code null} for none
   */
  public Litter setBrand(java.lang.String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCreated() {
    return created;
  }

  /**
   * @param created created or {@code null} for none
   */
  public Litter setCreated(com.google.api.client.util.DateTime created) {
    this.created = created;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Litter setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude latitude or {@code null} for none
   */
  public Litter setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLitterType() {
    return litterType;
  }

  /**
   * @param litterType litterType or {@code null} for none
   */
  public Litter setLitterType(java.lang.String litterType) {
    this.litterType = litterType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude longitude or {@code null} for none
   */
  public Litter setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhotoBlobRef() {
    return photoBlobRef;
  }

  /**
   * @param photoBlobRef photoBlobRef or {@code null} for none
   */
  public Litter setPhotoBlobRef(java.lang.String photoBlobRef) {
    this.photoBlobRef = photoBlobRef;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserEmail() {
    return userEmail;
  }

  /**
   * @param userEmail userEmail or {@code null} for none
   */
  public Litter setUserEmail(java.lang.String userEmail) {
    this.userEmail = userEmail;
    return this;
  }

  @Override
  public Litter set(String fieldName, Object value) {
    return (Litter) super.set(fieldName, value);
  }

  @Override
  public Litter clone() {
    return (Litter) super.clone();
  }

}
